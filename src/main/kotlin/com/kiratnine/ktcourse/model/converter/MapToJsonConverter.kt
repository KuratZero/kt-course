package com.kiratnine.ktcourse.model.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Converter
class MapToJsonConverter : AttributeConverter<Map<String, String>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, String>?): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, String> {
        return objectMapper.readValue(dbData ?: "{}")
    }
}