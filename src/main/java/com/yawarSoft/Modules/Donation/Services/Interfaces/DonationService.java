package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import org.springframework.data.domain.Page;

public interface DonationService {
    Long createDonation(DonationCreateRequest donationCreateRequest);
    DonationResponseDTO updateDonation(Long donationId, DonationUpdateDTO donationUpdateDTO);
    Page<DonationByDonorDTO> getDonationsByDonor(String documentType,String documentNumber, int page, int size);


    boolean updateInterviewAnswer(Long donationId, Long interviewAnswerId);
    boolean updatePhysicalAssessment(Long donationId, Long physicalAssessmentId);
    boolean updateBloodExtraction(Long donationId, Long bloodExtractionId);
}
