package com.yawarSoft.Modules.Login.Dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","message","jwt","status"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status) {
}