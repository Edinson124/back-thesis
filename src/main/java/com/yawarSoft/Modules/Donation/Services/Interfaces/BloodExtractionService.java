package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;

public interface BloodExtractionService {
    BloodExtractionDTO createBloodExtraction(BloodExtractionRequest bloodExtractionRequest);
    BloodExtractionDTO updateBloodExtraction(Long id, BloodExtractionRequest bloodExtractionRequest);
    BloodExtractionDTO getBloodExtraction(Long idDonation);
}
