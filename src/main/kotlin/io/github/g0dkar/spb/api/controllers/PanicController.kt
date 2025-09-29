package io.github.g0dkar.spb.api.controllers

import io.github.g0dkar.spb.api.requests.PanicStatusRequest
import io.github.g0dkar.spb.api.responses.PanicStatusResponse
import io.github.g0dkar.spb.api.responses.toResponse
import io.github.g0dkar.spb.domain.services.PanicStatusService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
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
    @Secured("panic-write")
    fun setPanicStatus(@RequestBody @NotNull @Valid newStatus: PanicStatusRequest?): ResponseEntity<PanicStatusResponse> =
        service.setStatus(newStatus!!.status!!, newStatus.metadata)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it.toResponse()) }
}
