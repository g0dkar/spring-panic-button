package io.github.g0dkar.spb.domain.repositories

import io.github.g0dkar.spb.domain.model.PanicStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PanicStatusRepository : JpaRepository<PanicStatus, UUID> {
    @Query("FROM PanicStatus ORDER BY timestamp DESC LIMIT 1")
    fun findCurrentStatus(): PanicStatus?
}
