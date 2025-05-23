package com.yawarSoft.Modules.Admin.Dto.Request;

import com.yawarSoft.Modules.Admin.Dto.BloodBankNetworkDetailsDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BBNetworkCreateDTO {
    private String name;
    private String description;
    private Set<Integer> idBloodBanks;
}
