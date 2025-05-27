package com.yawarSoft.Modules.Donation.Controllers;

import com.yawarSoft.Modules.Donation.Dto.InterviewAnswerDTO;
import com.yawarSoft.Modules.Donation.Dto.PhysicalAssessmentDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.PhysicalAssessmentRequest;
import com.yawarSoft.Modules.Donation.Services.Interfaces.PhysicalAssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physical")
public class PhysicalAssessmentController {

    private final PhysicalAssessmentService physicalAssessmentService;

    public PhysicalAssessmentController(PhysicalAssessmentService physicalAssessmentService) {
        this.physicalAssessmentService = physicalAssessmentService;
    }

    @PostMapping
    public PhysicalAssessmentDTO createPhysicalAssessment(@RequestBody PhysicalAssessmentRequest physicalAssessmentRequest) {
        return physicalAssessmentService.createPhysicalAssessment(physicalAssessmentRequest);
    }

    @PutMapping("/{id}")
    public PhysicalAssessmentDTO updatePhysicalAssessment(@PathVariable("id") Long id, @RequestBody PhysicalAssessmentRequest physicalAssessmentRequest){
        return physicalAssessmentService.updatePhysicalAssessment(id, physicalAssessmentRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalAssessmentDTO> getPhysicalAssessment(@PathVariable("id") Long idDonation) {
        PhysicalAssessmentDTO physicalAssessmentDTO = physicalAssessmentService.getPhysicalAssessment(idDonation);
        if (physicalAssessmentDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(physicalAssessmentDTO);
    }

}
