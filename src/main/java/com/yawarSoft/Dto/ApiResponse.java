package com.yawarSoft.Dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse {
    @JsonProperty("status")
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payload")
    private Object payload;

    public ApiResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ApiResponse(HttpStatus status, String message, Object payload) {
        this(status, message);
        this.payload = payload;
    }
}
