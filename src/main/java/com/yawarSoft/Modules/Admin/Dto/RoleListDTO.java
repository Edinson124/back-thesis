package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleListDTO {

    private Integer id;
    private String name;
    private String status;
    private Boolean isDeletable;
}
