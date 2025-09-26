package io.github.g0dkar.spb.api.controllers

import io.github.g0dkar.spb.api.responses.PanicStatusResponse
import io.github.g0dkar.spb.api.responses.toResponse
import io.github.g0dkar.spb.domain.services.PanicStatusService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PanicStatusController(
    private val service: PanicStatusService,
) {
    @GetMapping("/panic")
    fun panicStatus(): ResponseEntity<PanicStatusResponse> =
        service.currentStatus().let {
            ResponseEntity
                .status(it.status.httpStatus)
                .body(it.toResponse())
        }
}
