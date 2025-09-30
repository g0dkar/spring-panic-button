package io.github.g0dkar.spb.domain.repositories

import io.github.g0dkar.spb.domain.model.PanicStatus
import io.github.g0dkar.spb.domain.model.PanicStatuses
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PanicStatusRepository : JpaRepository<PanicStatus, UUID> {
    @Query("FROM PanicStatus ORDER BY timestamp DESC LIMIT 1")
    fun findCurrentStatus(): PanicStatus?

    fun findAllByStatusIn(
        panicStatuses: Set<PanicStatuses>,
        pageable: Pageable,
    ): List<PanicStatus>

    fun findAllByStatusInAndDeclaredById(
        panicStatuses: Set<PanicStatuses>,
        declaredById: String,
        pageable: Pageable,
    ): List<PanicStatus>

    fun findAllByStatusInAndDeclaredByLikeIgnoreCase(
        panicStatuses: Set<PanicStatuses>,
        declaredByLike: String,
        pageable: Pageable,
    ): List<PanicStatus>

    fun countByStatusIn(panicStatuses: Set<PanicStatuses>): Long
    fun countByStatusInAndDeclaredById(panicStatuses: Set<PanicStatuses>, declaredById: String): Long
    fun countByStatusInAndDeclaredByLikeIgnoreCase(panicStatuses: Set<PanicStatuses>, declaredByLike: String): Long
}
