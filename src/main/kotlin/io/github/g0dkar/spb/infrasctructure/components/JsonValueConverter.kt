package io.github.g0dkar.spb.infrasctructure.components

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.slf4j.LoggerFactory

@Converter
class JsonValueConverter(
    private val objectMapper: ObjectMapper,
) : AttributeConverter<Map<String, Any?>, String> {
    companion object {
        private val log = LoggerFactory.getLogger(JsonValueConverter::class.java)
    }

    override fun convertToDatabaseColumn(attribute: Map<String, Any?>?): String? =
        attribute
            ?.takeIf { it.isNotEmpty() }
            ?.let {
                try {
                    objectMapper.writeValueAsString(attribute)
                } catch (e: Exception) {
                    log.error("Error writing map as json", e)
                    throw IllegalArgumentException("Failed to serialize element to json", e)
                }
            }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any?> =
        try {
            dbData
                ?.let { objectMapper.readValue<Map<String, Any?>>(it) }
                ?: emptyMap()
        } catch (e: Exception) {
            log.error("Error reading json value", e)
            throw IllegalArgumentException("Failed to deserialize json value", e)
        }
}
