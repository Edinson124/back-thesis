package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BBNetworkDTO {

    private Integer id;
    private String name;
    private String description;
    private String status;

    private Set<BloodBankNetworkDetailsDTO> bloodBanks;
}
