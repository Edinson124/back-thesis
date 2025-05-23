package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetworkDTO {

    private Integer id;
    private String name;
    private String description;

    List<BloodBankNetworkDetailsDTO> bloodBankDetails;
}
