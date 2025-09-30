package io.github.g0dkar.spb.domain.services

import io.github.g0dkar.spb.domain.model.PanicStatus
import io.github.g0dkar.spb.domain.model.PanicStatuses
import io.github.g0dkar.spb.domain.repositories.PanicStatusRepository
import io.github.g0dkar.spb.infrasctructure.extensions.isUUID
import io.github.g0dkar.spb.infrasctructure.extensions.takeIfValue
import io.github.robsonkades.uuidv7.UUIDv7
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class PanicStatusService(
    private val repository: PanicStatusRepository,
    @Value("\${panic-button.security.anonymousUsername}")
    private val anonymousUsername: String,
) {
    fun currentStatus(): PanicStatus =
        repository.findCurrentStatus()
            ?: throw IllegalStateException("Unable to determine current status. PANIC!")

    fun list(
        page: Int,
        size: Int,
        statuses: Set<PanicStatuses?>,
        declaredBy: String? = null,
        orderBy: String = "timestamp",
        orderByDirection: Sort.Direction = Sort.Direction.DESC,
    ): Triple<List<PanicStatus>, Long, String> {
        val orderByField = orderBy.takeIfValue("timestamp", "status", "timestamp", "declaredBy")
        val panicStatuses: Set<PanicStatuses> =
            (statuses.filterNotNull().takeIf { it.isNotEmpty() } ?: PanicStatuses.entries).toSet()
        val pageable = PageRequest.of(page, size, Sort.by(orderByDirection, orderByField))

        val pageData = when (declaredBy) {
            null -> repository.findAllByStatusIn(panicStatuses, pageable)
            else -> {
                if (declaredBy.isUUID()) {
                    repository.findAllByStatusInAndDeclaredById(panicStatuses, declaredBy, pageable)
                } else {
                    repository.findAllByStatusInAndDeclaredByLikeIgnoreCase(panicStatuses, declaredBy, pageable)
                }
            }
        }

        val countData = when (declaredBy) {
            null -> repository.countByStatusIn(panicStatuses)
            else -> {
                if (declaredBy.isUUID()) {
                    repository.countByStatusInAndDeclaredById(panicStatuses, declaredBy)
                } else {
                    repository.countByStatusInAndDeclaredByLikeIgnoreCase(panicStatuses, declaredBy)
                }
            }
        }

        return Triple(pageData, countData, orderByField)
    }

    @Transactional(rollbackFor = [Throwable::class])
    fun setStatus(
        status: PanicStatuses,
        metadata: Map<String?, Any?>?,
        declaredBy: String? = null,
        declaredById: String? = null,
    ): PanicStatus =
        PanicStatus(
            id = UUIDv7.randomUUID(),
            status = status,
            metadata = metadata?.let {
                mutableMapOf<String, Any?>().apply {
                    metadata.forEach { (key, value) ->
                        if (!key.isNullOrBlank()) {
                            this[key] = value
                        }
                    }
                }.takeIf { it.isNotEmpty() }
            },
            timestamp = OffsetDateTime.now(),
            declaredBy = declaredBy.takeUnless { it.isNullOrBlank() } ?: anonymousUsername,
            declaredById = declaredById.takeUnless { it.isNullOrBlank() },
        ).also { repository.saveAndFlush(it) }
}
