package com.yawarSoft.Modules.Network.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Network.Dto.Request.RequestDetailsUnitDTO;
import com.yawarSoft.Modules.Network.Dto.ShipmentXUnitDTO;
import com.yawarSoft.Modules.Network.Services.Interfaces.ShipmentAssignmentService;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shipment-assignment")
public class ShipmentAssignmentController {

    private final ShipmentAssignmentService shipmentAssignmentService;

    public ShipmentAssignmentController(ShipmentAssignmentService shipmentAssignmentService) {
        this.shipmentAssignmentService = shipmentAssignmentService;
    }

    @PostMapping("/{idShipmentRequest}/{idUnit}")
    public ShipmentXUnitDTO saveShipmentAssignment(@PathVariable Long idUnit, @PathVariable Integer idShipmentRequest) {
        return shipmentAssignmentService.saveShipmentAssignment(idShipmentRequest, idUnit);
    }

    @DeleteMapping("/{idShipmentAssignment}")
    public ResponseEntity<ApiResponse> deleteShipmentAssignment(@PathVariable Integer idShipmentAssignment) {
        Integer id = shipmentAssignmentService.deleteShipmentAssignment(idShipmentAssignment);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK, "Eliminacion asignación de unidades a solicitud de transferencia exitosa", payload));
    }
}
