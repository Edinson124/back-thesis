package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.DonationRelationsDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DeferralDonationRequest;
import com.yawarSoft.Modules.Donation.Dto.Request.DonationCreateRequest;
import com.yawarSoft.Modules.Donation.Dto.DonationUpdateDTO;
import com.yawarSoft.Modules.Donation.Dto.DonationResponseDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DateDonationDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationByDonorDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.DonationGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Response.ExistDonationDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DonationService {
    Long createDonation(DonationCreateRequest donationCreateRequest);
    DonationResponseDTO updateDonation(Long donationId, DonationUpdateDTO donationUpdateDTO);
    Page<DonationByDonorDTO> getDonationsByDonor(String documentType,String documentNumber, int page, int size);
    DonationResponseDTO getActualDonation(String documentType,String documentNumber);

    DateDonationDTO getDateDetailLastDonation(String documentType, String documentNumber);

    boolean updateInterviewAnswer(Long donationId, Long interviewAnswerId);
    boolean updatePhysicalAssessment(Long donationId, Long physicalAssessmentId);
    boolean updateBloodExtraction(Long donationId, Long bloodExtractionId);

    boolean updateSerologyTest(Long donationId, Long serologyTestId);
    boolean updateHematologicalTest(Long donationId, Long hematologicalTestId);

    ExistDonationDTO existsByCode(Long id);
    ExistDonationDTO existsActualByDonor(String documentType, String documentNumber);

    DonationGetDTO getDonationById(Long id);
    boolean updateDonationReactiveTestSeorologyById(Long id);
    boolean updateDonationFinishedById(Long id, String status);

    DonationRelationsDTO getIdsRelations(Long id);
    boolean updateDonorBloodType(Long donationId, String bloodType, String rhFactor);

    Map<String, String> getBloodTypeAndSerology(Long id);

    Long finishDonationWithDeferral(Long idDonation, DeferralDonationRequest deferralDonationRequest);

    String getDonationStatus(Long id);
}
