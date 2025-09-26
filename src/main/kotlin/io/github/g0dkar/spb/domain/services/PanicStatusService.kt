package io.github.g0dkar.spb.domain.services

import io.github.g0dkar.spb.domain.model.PanicStatus
import io.github.g0dkar.spb.domain.repositories.PanicStatusRepository
import org.springframework.stereotype.Service

@Service
class PanicStatusService(
    private val repository: PanicStatusRepository,
) {
    fun currentStatus(): PanicStatus =
        repository.findCurrentStatus()
            ?: throw IllegalStateException("Unable to determine current status. PANIC!")
}
