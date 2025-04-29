package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.BloodExtractionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blood-extraction")
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
    public BloodExtractionDTO getBloodExtraction(@PathVariable("id") Long id) {
        return bloodExtractionService.getBloodExtraction(id);
    }
}
