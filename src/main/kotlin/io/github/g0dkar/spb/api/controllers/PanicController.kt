package io.github.g0dkar.spb.api.controllers

import io.github.g0dkar.spb.api.requests.PanicStatusRequest
import io.github.g0dkar.spb.api.responses.ListingResponse
import io.github.g0dkar.spb.api.responses.PanicStatusResponse
import io.github.g0dkar.spb.api.responses.mapToListingResponse
import io.github.g0dkar.spb.api.responses.toResponse
import io.github.g0dkar.spb.domain.model.PanicStatuses
import io.github.g0dkar.spb.domain.services.PanicStatusService
import io.github.g0dkar.spb.infrasctructure.extensions.getKeycloakUser
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/panic")
class PanicController(
    private val service: PanicStatusService,
) {
    companion object {
        private val log = LoggerFactory.getLogger(PanicController::class.java)
    }

    @GetMapping
    fun getPanicStatus(
        @RequestParam(required = false, defaultValue = "false") quiet: Boolean = false,
    ): ResponseEntity<PanicStatusResponse> =
        service.currentStatus()
            .let {
                ResponseEntity
                    .status(it.httpStatus)
                    .let { response ->
                        when (quiet) {
                            true -> response.build()
                            false -> response.body(it.toResponse())
                        }
                    }
            }

    @GetMapping("/all")
    @Secured("spring-panic-button-backend.panic-read")
    fun listPanicStatuses(
        @RequestParam(required = false) @Min(0) @Max(10_000_000) page: Int = 0,
        @RequestParam(required = false) @Min(1) @Max(1_000) size: Int = 10,
        @RequestParam(required = false) statuses: Set<PanicStatuses?>? = null,
        @RequestParam(required = false, name = "declared_by") declaredBy: String? = null,
        @RequestParam(
            required = false,
            name = "order",
            defaultValue = "timestamp",
        ) @NotBlank orderBy: String? = "timestamp",
        @RequestParam(
            required = false,
            name = "sort",
            defaultValue = "DESC",
        ) @NotNull orderByDirection: Sort.Direction? = Sort.Direction.DESC,
    ): ResponseEntity<ListingResponse<PanicStatusResponse>> =
        service.list(
            page,
            size,
            statuses ?: emptySet(),
            declaredBy,
            orderBy ?: "timestamp",
            orderByDirection ?: Sort.Direction.DESC,
        ).let { (pageData, totalItemsCount, orderByField) ->
            pageData
                .mapToListingResponse(page, size, totalItemsCount, orderByField) { it.toResponse(withId = true) }
                .let { ResponseEntity.ok(it) }
        }

    @PostMapping
    @Secured("spring-panic-button-backend.panic-write")
    fun setPanicStatus(
        @RequestBody @NotNull @Valid newStatus: PanicStatusRequest?,
        authToken: JwtAuthenticationToken?,
    ): ResponseEntity<PanicStatusResponse> =
        authToken?.principal.getKeycloakUser()
            .let { (userId, userName) ->
                service.setStatus(
                    status = newStatus!!.status!!,
                    metadata = newStatus.metadata,
                    declaredBy = userName,
                    declaredById = userId,
                )
            }
            .let { ResponseEntity.status(CREATED).body(it.toResponse()) }
}
