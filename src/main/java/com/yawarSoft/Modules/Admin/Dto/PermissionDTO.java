package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

    private Integer id;
    private String name;
    private String description;
    private Boolean allBloodBankType;
}
