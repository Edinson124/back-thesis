package com.yawarSoft.Modules.Network.Dto.Response;

import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Request.RequestDetailsUnitDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestDataDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private Boolean canViewRequest ;
    private BloodBankNetworkCollaborationDTO bloodBankOrigin;
    private ShipmentRequestDataDTO shipmentRequest;
    private List<RequestDetailsUnitDTO> units;
}
