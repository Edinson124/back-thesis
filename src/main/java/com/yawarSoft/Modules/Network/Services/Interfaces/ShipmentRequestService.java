package com.yawarSoft.Modules.Network.Services.Interfaces;

import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface ShipmentRequestService {
    Page<ShipmentRequestTableDTO> getShipments(int page, int size, LocalDate startEntryDate,
                                                               LocalDate endEntryDate, String status,
                                                               Long code, Integer idBloodBank);

    Page<ShipmentRequestTableDTO> getMyShipments(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, String status, Long code, Integer idBloodBank);
}
