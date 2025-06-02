package com.yawarSoft.Modules.Network.Controllers;

import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Response.StockNetworkDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentRequestService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentRequestController {
    private final ShipmentRequestService shipmentRequestService;

    public ShipmentRequestController(ShipmentRequestService shipmentRequestService) {
        this.shipmentRequestService = shipmentRequestService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ShipmentRequestTableDTO>> getShipments(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startEntryDate,
                                                                      @RequestParam(required = false)
                                         @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endEntryDate,
                                                                      @RequestParam(required = false) String status,
                                                                      @RequestParam(required = false) Long code,
                                                                      @RequestParam(required = false) Integer idBloodBank) {
        Page<ShipmentRequestTableDTO> shipments =  shipmentRequestService.getShipments(page, size, startEntryDate, endEntryDate,
                status, code, idBloodBank);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<ShipmentRequestTableDTO>> getMyShipments(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @RequestParam(required = false)
                                                                      @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startEntryDate,
                                                                      @RequestParam(required = false)
                                                                      @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endEntryDate,
                                                                      @RequestParam(required = false) String status,
                                                                      @RequestParam(required = false) Long code,
                                                                      @RequestParam(required = false) Integer idBloodBank) {
        Page<ShipmentRequestTableDTO> shipments =  shipmentRequestService.getMyShipments(page, size, startEntryDate, endEntryDate,
                status, code, idBloodBank);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/networks")
    public ResponseEntity<List<NetworkCollaborationDTO>> getNetworkToShipments() {
        List<NetworkCollaborationDTO> networks =  shipmentRequestService.getNetworkToShipments();
        return ResponseEntity.ok(networks);
    }
}
