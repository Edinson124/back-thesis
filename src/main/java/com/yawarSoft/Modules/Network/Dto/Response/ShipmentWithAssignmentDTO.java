package com.yawarSoft.Modules.Network.Dto.Response;

import com.yawarSoft.Modules.Network.Dto.BloodBankNetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Request.RequestDetailsUnitDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestDataDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentXUnitDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentWithAssignmentDTO {
    private Boolean canViewRequest ;
    private BloodBankNetworkCollaborationDTO bloodBankOrigin;
    private BloodBankNetworkCollaborationDTO bloodBankDestination;
    private ShipmentRequestDataDTO shipmentRequest;
    private List<RequestDetailsUnitDTO> units;
    private List<ShipmentXUnitDTO> assignment;
}
