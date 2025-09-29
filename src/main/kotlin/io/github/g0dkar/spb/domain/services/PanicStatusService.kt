package io.github.g0dkar.spb.domain.services

import io.github.g0dkar.spb.domain.model.PanicMetadata
import io.github.g0dkar.spb.domain.model.PanicStatus
import io.github.g0dkar.spb.domain.model.PanicStatuses
import io.github.g0dkar.spb.domain.repositories.PanicStatusRepository
import io.github.robsonkades.uuidv7.UUIDv7
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class PanicStatusService(
    private val repository: PanicStatusRepository,
    @param:Value("\${panic-button.security.anonymousUsername}")
    private val anonymousUsername: String,
) {
    fun currentStatus(): PanicStatus =
        repository.findCurrentStatus()
            ?: throw IllegalStateException("Unable to determine current status. PANIC!")

    @Transactional(rollbackFor = [Throwable::class])
    fun setStatus(
        status: PanicStatuses,
        metadata: PanicMetadata?,
        declaredBy: String? = null,
        declaredById: String? = null,
    ): PanicStatus =
        PanicStatus(
            id = UUIDv7.randomUUID(),
            status = status,
            metadata = metadata,
            timestamp = OffsetDateTime.now(),
            declaredBy = declaredBy.takeUnless { it.isNullOrBlank() } ?: anonymousUsername,
            declaredById = declaredById.takeUnless { it.isNullOrBlank() },
        ).also { repository.saveAndFlush(it) }
}
