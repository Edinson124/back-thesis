package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import com.yawarSoft.Modules.Donation.Dto.PhysicalAssessmentDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.BloodExtractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blood-extraction")
public class BloodExtractionController {

    private final BloodExtractionService bloodExtractionService;


    public BloodExtractionController(BloodExtractionService bloodExtractionService) {
        this.bloodExtractionService = bloodExtractionService;
    }

    @PostMapping
    public BloodExtractionDTO createBloodExtraction(@RequestBody BloodExtractionRequest bloodExtractionRequest) {
        return bloodExtractionService.createBloodExtraction(bloodExtractionRequest);
    }

    @PutMapping("/{id}")
    public BloodExtractionDTO updateBloodExtraction(@PathVariable("id") Long id, @RequestBody BloodExtractionRequest bloodExtractionRequest) {
        return bloodExtractionService.updateBloodExtraction(id, bloodExtractionRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodExtractionDTO> getBloodExtraction(@PathVariable("id") Long idDonation) {
        BloodExtractionDTO bloodExtractionDTO = bloodExtractionService.getBloodExtraction(idDonation);
        if (bloodExtractionDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bloodExtractionDTO);
    }
}
