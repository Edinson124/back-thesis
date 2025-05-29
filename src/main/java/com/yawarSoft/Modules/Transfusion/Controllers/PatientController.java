package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Transfusion.Dto.PatientGetDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.GetPatientRequest;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientDocumentRequest;
import com.yawarSoft.Modules.Transfusion.Dto.Request.PatientRequestDTO;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/validate")
    public Map<String, String> validatePatient(@RequestBody GetPatientRequest infoPatientRequest) {
        String fullName = patientService.getFullNamePatient(infoPatientRequest.documentType(),
                infoPatientRequest.documentNumber());

        Map<String, String> response = new HashMap<>();
        response.put("name", fullName);
        return response;
    }

    @PostMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> checkIfPatientExists(@RequestBody PatientDocumentRequest request) {
        Boolean exists = patientService.existsByDocument(request.documentType(), request.documentNumber());
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/search")
    public ResponseEntity<?> getPatient(@RequestBody PatientDocumentRequest request) {
        PatientGetDTO responseDTO = patientService.getPatient(request.documentType(),request.documentNumber());
        if (responseDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        Long id = patientService.createPatient(patientRequestDTO);
        Map<String, Object> payload = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED, "Paciente creado exitosamente", payload));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updatePatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        PatientGetDTO patientGetDTO = patientService.updatePatient(patientRequestDTO);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Paciente actualizado exitosamente", patientGetDTO));
    }
}
