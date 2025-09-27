package io.github.g0dkar.spb.domain.services

import io.github.g0dkar.spb.domain.model.PanicMetadata
import io.github.g0dkar.spb.domain.model.PanicStatus
import io.github.g0dkar.spb.domain.model.PanicStatuses
import io.github.g0dkar.spb.domain.repositories.PanicStatusRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
class PanicStatusService(
    private val repository: PanicStatusRepository,
) {
    fun currentStatus(): PanicStatus =
        repository.findCurrentStatus()
            ?: throw IllegalStateException("Unable to determine current status. PANIC!")

    @Transactional(rollbackFor = [Throwable::class])
    fun setStatus(status: PanicStatuses, metadata: PanicMetadata?): PanicStatus =
        PanicStatus(
            id = UUID.randomUUID(),
            status = status,
            metadata = metadata,
            timestamp = OffsetDateTime.now(),
            declaredBy = "PanicStatusService",
        ).also { repository.saveAndFlush(it) }
}
