package com.yawarSoft.Modules.Interoperability.Dtos.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthExternalSystemRequestDTO {
    private String description;
    private String clientUser;
    private String clientSecret;
}
