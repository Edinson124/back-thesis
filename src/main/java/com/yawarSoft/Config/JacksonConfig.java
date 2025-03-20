package com.yawarSoft.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Formato personalizado para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));

        return JsonMapper.builder()
                .addModule(javaTimeModule) // Soporte para fechas Java 8+
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // Evita el array [YYYY, MM, DD]
                .build();
    }

}