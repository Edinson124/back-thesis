package com.yawarSoft.Modules.Network.Dto.Response;

import com.yawarSoft.Modules.Network.Dto.UnitCollaborationTableDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockNetworkDTO {

    private Boolean canViewUser;
    private Page<UnitCollaborationTableDto> unitsStock;
}
