package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.PhysicalAssessmentDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.PhysicalAssessmentRequest;

public interface PhysicalAssessmentService {
    PhysicalAssessmentDTO createPhysicalAssessment(PhysicalAssessmentRequest physicalAssessmentRequest);
    PhysicalAssessmentDTO updatePhysicalAssessment(Long id,PhysicalAssessmentRequest physicalAssessmentRequest);
    PhysicalAssessmentDTO getPhysicalAssessment(Long idDonation);
}
