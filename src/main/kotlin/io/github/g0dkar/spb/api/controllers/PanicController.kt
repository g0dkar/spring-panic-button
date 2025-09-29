package io.github.g0dkar.spb.api.controllers

import io.github.g0dkar.spb.api.requests.PanicStatusRequest
import io.github.g0dkar.spb.api.responses.PanicStatusResponse
import io.github.g0dkar.spb.api.responses.toResponse
import io.github.g0dkar.spb.domain.services.PanicStatusService
import io.github.g0dkar.spb.infrasctructure.extensions.getKeycloakUser
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
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
    @GetMapping
    fun getPanicStatus(
        @RequestParam(required = false, defaultValue = "false") quiet: Boolean = false,
    ): ResponseEntity<PanicStatusResponse> =
        service.currentStatus().let {
            ResponseEntity
                .status(it.httpStatus)
                .let { response ->
                    if (!quiet) {
                        response.body(it.toResponse())
                    } else {
                        response.build()
                    }
                }
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
