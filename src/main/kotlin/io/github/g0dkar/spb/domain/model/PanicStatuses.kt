package io.github.g0dkar.spb.domain.model

import org.springframework.http.HttpStatus

enum class PanicStatuses(val httpStatus: HttpStatus) {
    OK(HttpStatus.OK),
    WARNING(HttpStatus.OK),
    OUTAGE(HttpStatus.BAD_GATEWAY),
    MAJOR_OUTAGE(HttpStatus.SERVICE_UNAVAILABLE),
}
