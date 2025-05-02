package com.yawarSoft.Modules.Transfusion.Controllers;

import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;
import com.yawarSoft.Modules.Transfusion.Dto.Request.GetPatientRequest;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, String> getPatient(@RequestBody GetPatientRequest infoPatientRequest) {
        String fullName = patientService.getFullNamePatient(
                infoPatientRequest.documentType(),
                infoPatientRequest.documentNumber()
        );

        Map<String, String> response = new HashMap<>();
        response.put("name", fullName);
        return response;
    }
}
