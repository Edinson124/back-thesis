package com.yawarSoft.Modules.Network.Dto.Response;

import com.yawarSoft.Modules.Network.Dto.SerologyTestNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.UnitNetworkDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitInfoCollaboration {
    private Boolean canViewUnit ;
    private UnitNetworkDTO unit;
    private SerologyTestNetworkDTO serology;
}
