package io.github.g0dkar.spb.api.responses

import io.github.g0dkar.spb.domain.model.PanicMetadata
import io.github.g0dkar.spb.domain.model.PanicStatus
import java.time.OffsetDateTime
import java.util.UUID

data class PanicStatusResponse(
    val id: UUID?,
    val status: String,
    val metadata: PanicMetadata?,
    val timestamp: OffsetDateTime,
    val declaredBy: String,
    val declaredById: String?,
)

fun PanicStatus.toResponse(withId: Boolean = false): PanicStatusResponse =
    PanicStatusResponse(
        id = id.takeIf { withId },
        status = status.name,
        metadata = metadata,
        timestamp = timestamp,
        declaredBy = declaredBy,
        declaredById = declaredById,
    )
