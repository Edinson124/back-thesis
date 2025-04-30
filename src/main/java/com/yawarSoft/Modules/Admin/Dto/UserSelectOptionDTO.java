package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSelectOptionDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;

    public String getFullName() {
        return String.format("%s %s %s", firstName, lastName, secondLastName != null ? secondLastName : "").trim();
    }
}
