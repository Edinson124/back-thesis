package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Transfusion.Dto.BloodTypeOption;
import com.yawarSoft.Modules.Transfusion.Dto.Request.DispensedAssignUnitRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionAssignResultDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionAssignmentService;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionBloodCompatible;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transfusion-assignment")
public class TransfusionAssignmentController {

    private final TransfusionAssignmentService transfusionAssignmentService;
    private final TransfusionBloodCompatible transfusionBloodCompatible;

    public TransfusionAssignmentController(TransfusionAssignmentService transfusionAssignmentService, TransfusionBloodCompatible transfusionBloodCompatible) {
        this.transfusionAssignmentService = transfusionAssignmentService;
        this.transfusionBloodCompatible = transfusionBloodCompatible;
    }

    @GetMapping("/bloodCompatbile/{idTransfusion}")
    public List<BloodTypeOption> getBloodTypeCompatible(@PathVariable Long idTransfusion) {
        return transfusionBloodCompatible.getBloodTypeCompatible(idTransfusion);
    }

    @PostMapping("/{idTransfusion}/{idUnit}")
    public TransfusionAssignmentDTO saveTransfusionAssignment(@PathVariable Long idUnit, @PathVariable Long idTransfusion) {
        return transfusionAssignmentService.saveTransfusionAssignment(idTransfusion, idUnit);
    }

    @DeleteMapping("/{idTransfusionAssignment}")
    public ResponseEntity<ApiResponse> deleteTransfusionAssignment(@PathVariable Long idTransfusionAssignment) {
        Long id = transfusionAssignmentService.deleteTransfusionAssignment(idTransfusionAssignment);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK, "Eliminacion asignación de unidades a tranfusión exitosa", payload));
    }

    @PutMapping("/{idTransfusionAssignment}")
    public TransfusionAssignmentDTO saveValidateResult(@PathVariable Long idTransfusionAssignment, @RequestBody TransfusionAssignResultDTO request) {
        return transfusionAssignmentService.saveValidateResult(idTransfusionAssignment, request);
    }

    @PutMapping("/dispensed/{idTransfusion}")
    public ResponseEntity<?> dispensedUnits(@PathVariable Long idTransfusion,
                                                      @RequestBody DispensedAssignUnitRequestDTO requestDTO) {
        Long id = transfusionAssignmentService.dispensedUnits(idTransfusion, requestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK, "Liberar unidades de asignadas a tranfusión exitosa", payload));
    }
}
