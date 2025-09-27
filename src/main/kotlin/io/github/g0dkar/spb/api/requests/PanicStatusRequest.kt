package io.github.g0dkar.spb.api.requests

import io.github.g0dkar.spb.domain.model.PanicMetadata
import io.github.g0dkar.spb.domain.model.PanicStatuses
import jakarta.validation.constraints.NotNull

data class PanicStatusRequest(
    @field:NotNull
    val status: PanicStatuses?,
    val metadata: PanicMetadata?,
)
