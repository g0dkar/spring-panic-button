package io.github.g0dkar.spb.domain.model

import io.github.g0dkar.spb.infrasctructure.components.JsonValueConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.UUID
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "panic_status")
data class PanicStatus(
    @Id @GeneratedValue(strategy = UUID)
    val id: UUID,

    @Column(name = "status")
    @Enumerated(STRING)
    val status: PanicStatuses,

    @Column(name = "metadata", nullable = true)
    @Convert(converter = JsonValueConverter::class)
    val metadata: Map<String, Any?>?,

    @Column(name = "timestamp")
    val timestamp: OffsetDateTime,

    @Column(name = "declared_by")
    val declaredBy: String,
)
