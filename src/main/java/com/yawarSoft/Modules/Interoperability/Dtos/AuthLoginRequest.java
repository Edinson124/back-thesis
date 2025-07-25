package com.yawarSoft.Modules.Interoperability.Dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"username","message","jwt","status"})
public record AuthLoginRequest(@NotBlank String username,
                               @NotBlank String password) {
}
