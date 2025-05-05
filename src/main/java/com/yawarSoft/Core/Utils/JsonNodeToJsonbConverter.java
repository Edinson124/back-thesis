package com.yawarSoft.Core.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class JsonNodeToJsonbConverter implements AttributeConverter<JsonNode, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null) return null;
        try {
            // Convierte el JsonNode a un String JSON válido
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error convirtiendo JsonNode a String JSON", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            // Convierte el String JSON desde la base de datos a JsonNode
            return objectMapper.readTree(dbData);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error convirtiendo String JSON a JsonNode", e);
        }
    }
}
