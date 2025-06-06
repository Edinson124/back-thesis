package com.yawarSoft.Modules.Network.Services.Interfaces;

import com.yawarSoft.Modules.Network.Dto.ShipmentXUnitDTO;

public interface ShipmentAssignmentService {
    ShipmentXUnitDTO saveShipmentAssignment(Integer idShipmentRequest, Long idUnit);

    Integer deleteShipmentAssignment(Integer idShipmentAssignment);
}
