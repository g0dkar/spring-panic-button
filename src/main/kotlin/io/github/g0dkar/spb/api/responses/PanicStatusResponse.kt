package io.github.g0dkar.spb.api.responses

import io.github.g0dkar.spb.domain.model.PanicMetadata
import io.github.g0dkar.spb.domain.model.PanicStatus
import java.time.OffsetDateTime

data class PanicStatusResponse(
    val status: String,
    val metadata: PanicMetadata?,
    val since: OffsetDateTime,
    val declaredBy: String,
    val declaredById: String?,
)

fun PanicStatus.toResponse(): PanicStatusResponse =
    PanicStatusResponse(
        status = status.name,
        metadata = metadata,
        since = timestamp,
        declaredBy = declaredBy,
        declaredById = declaredById,
    )
