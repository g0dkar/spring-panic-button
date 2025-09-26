package io.github.g0dkar.spb.api.responses

import io.github.g0dkar.spb.domain.model.PanicStatus
import java.time.OffsetDateTime

data class PanicStatusResponse(
    val generalStatus: String,
    val metadata: Map<String, Any?>?,
    val since: OffsetDateTime,
    val declaredBy: String,
)

fun PanicStatus.toResponse(): PanicStatusResponse =
    PanicStatusResponse(
        generalStatus = status.name,
        metadata = metadata,
        since = timestamp,
        declaredBy = declaredBy,
    )
