package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.BloodExtractionDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.BloodExtractionRequest;
import org.springframework.stereotype.Repository;

public interface BloodExtractionService {
    BloodExtractionDTO createBloodExtraction(BloodExtractionRequest bloodExtractionRequest);
    BloodExtractionDTO updateBloodExtraction(Long id, BloodExtractionRequest bloodExtractionRequest);
    BloodExtractionDTO getBloodExtraction(Long id);
}
