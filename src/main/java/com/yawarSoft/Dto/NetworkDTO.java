package com.yawarSoft.Dto;

import lombok.*;

import java.util.List;
import java.util.Set;

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
