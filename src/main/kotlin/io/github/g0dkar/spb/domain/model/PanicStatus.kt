package io.github.g0dkar.spb.domain.model

import io.github.g0dkar.spb.infrasctructure.components.JsonValueConverter
import io.github.g0dkar.spb.infrasctructure.extensions.toHttpStatusOrDefault
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "panic_status")
data class PanicStatus(
    @Id
    val id: UUID,

    @Column(name = "status")
    @Enumerated(STRING)
    val status: PanicStatuses,

    @Column(name = "metadata", nullable = true)
    @Convert(converter = JsonValueConverter::class)
    @ColumnTransformer(write = "?::jsonb")
    val metadata: PanicMetadata?,

    @Column(name = "timestamp")
    val timestamp: OffsetDateTime,

    @Column(name = "declared_by")
    val declaredBy: String,

    @Column(name = "declared_by_id", nullable = true)
    val declaredById: String?,
) {
    companion object {
        const val METADATA_HTTP_STATUS = "http_status"
    }

    val httpStatus: HttpStatus
        get() =
            if (metadata != null) {
                metadata[METADATA_HTTP_STATUS].toHttpStatusOrDefault(status.httpStatus)
            } else {
                status.httpStatus
            }
}

enum class PanicStatuses(val httpStatus: HttpStatus) {
    OK(HttpStatus.OK),
    WARNING(HttpStatus.GONE),
    OUTAGE(HttpStatus.BAD_GATEWAY),
    MAJOR_OUTAGE(HttpStatus.SERVICE_UNAVAILABLE),
}

typealias PanicMetadata = Map<String, Any?>
