package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Integer id;
    private String name;
    private String description;
    private Set<Integer> permissions;
    private String status;
}
