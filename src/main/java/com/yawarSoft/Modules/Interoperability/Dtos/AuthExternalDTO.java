package com.yawarSoft.Modules.Interoperability.Dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthExternalDTO {

    private Boolean hasPassword;
    private Integer id;

    private Integer bloodBankId;
    private String bloodBankName;

    private String description;
    private String clientUser;
    private Boolean isActive;
}
