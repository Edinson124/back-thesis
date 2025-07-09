package com.yawarSoft.Modules.Login.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private String role;
    private String bloodBankName;
    private String bloodBankType;
    private List<String> permissions;
}
