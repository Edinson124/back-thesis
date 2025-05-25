package com.yawarSoft.Modules.Network.Dto;

import com.yawarSoft.Modules.Admin.Dto.BloodBankNetworkDetailsDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetworkCollaborationDTO {

    private Integer id;
    private String name;
    private String description;

    List<BloodBankNetworkCollaborationDTO> bloodBankDetails;
}
