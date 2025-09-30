package io.github.g0dkar.spb.api.requests

import io.github.g0dkar.spb.domain.model.PanicStatuses
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PanicStatusRequest(
    @NotNull
    val status: PanicStatuses?,
    val metadata: Map<@NotBlank @Size(min = 1, max = 128) String?, Any?>?,
)
