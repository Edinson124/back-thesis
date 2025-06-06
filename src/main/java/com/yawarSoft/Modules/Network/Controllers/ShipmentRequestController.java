package com.yawarSoft.Modules.Network.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Network.Dto.NetworkCollaborationDTO;
import com.yawarSoft.Modules.Network.Dto.Request.ShipmentRequestDTO;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentDTO;
import com.yawarSoft.Modules.Network.Dto.Response.ShipmentWithAssignmentDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentRequestTableDTO;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentRequestService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @PostMapping()
    public ResponseEntity<ApiResponse> createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO) {
        Integer id =  shipmentRequestService.createShipment(shipmentRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Solicitud de transferencia creada exitosamente", payload));
    }

    @GetMapping("/{idShipment}")
    public ShipmentDTO getShipment(@PathVariable Integer idShipment) {
        return shipmentRequestService.getShipment(idShipment);
    }

    @PutMapping("/{idShipment}")
    public ResponseEntity<ApiResponse> editShipment(@PathVariable Integer idShipment,@RequestBody ShipmentRequestDTO shipmentRequestDTO) {
        Integer id = shipmentRequestService.editShipment(idShipment,shipmentRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Solicitud de transferencia editada exitosamente", payload));
    }

    @PutMapping("/send/{idShipment}")
    public ResponseEntity<ApiResponse> sendShipment(@PathVariable Integer idShipment) {
        Integer id = shipmentRequestService.sendShipment(idShipment);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Solicitud de transferencia editada exitosamente", payload));
    }

    @GetMapping("/assignment/{idShipment}")
    public ShipmentWithAssignmentDTO getShipmentWithAssignment(@PathVariable Integer idShipment) {
        return shipmentRequestService.getShipmentWithAssignment(idShipment);
    }
}
