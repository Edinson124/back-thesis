package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankSelectOptionDTO {
    private Integer id;
    private String name;
    private String bloodBankType;
}
