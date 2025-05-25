package com.yawarSoft.Modules.Network.Dto.Response;

import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionBloodBankNetworkDTO {
    private Boolean canViewUser;
    private List<BloodBankNetworkCollaborationDTO> bloodBanks;
}
