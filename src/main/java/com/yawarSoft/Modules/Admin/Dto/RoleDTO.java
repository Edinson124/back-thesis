package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private String name;
    private String description;
    private String typeBloodBank;
}
