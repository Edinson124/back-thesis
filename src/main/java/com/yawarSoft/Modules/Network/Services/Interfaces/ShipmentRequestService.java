package com.yawarSoft.Modules.Network.Services.Interfaces;

import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Request.ShipmentRequestDTO;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentWithAssignmentDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import org.springframework.data.domain.Page;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentRequestService {
    Page<ShipmentRequestTableDTO> getShipments(int page, int size, LocalDate startEntryDate,
                                                               LocalDate endEntryDate, String status,
                                                               Long code, Integer idBloodBank);

    Page<ShipmentRequestTableDTO> getMyShipments(int page, int size, LocalDate startEntryDate,
                                                 LocalDate endEntryDate, String status, Long code,
                                                 Integer idBloodBank);

    List<NetworkCollaborationDTO> getNetworkToShipments();

    Integer createShipment(ShipmentRequestDTO shipmentRequestDTO);

    ShipmentDTO getShipment(Integer idShipment);

    Integer editShipment(Integer idShipment, ShipmentRequestDTO shipmentRequestDTO);

    Integer sendShipment(Integer idShipment);

    ShipmentWithAssignmentDTO getShipmentWithAssignment(Integer idShipment, int mode);

    Integer freeUnits(Integer idShipment);

    Integer confirmReception(Integer idShipment);

    Integer declineShipment(Integer idShipment);
}
