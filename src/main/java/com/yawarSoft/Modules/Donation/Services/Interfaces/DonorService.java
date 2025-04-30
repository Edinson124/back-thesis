package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;

public interface DonorService {
    Long createDonor(DonorRequestDTO donorRequestDTO);
    Boolean existsByDocument(Long id, String documentType, String documentNumber);
    DonorGetDTO updateDonor(Long id, DonorRequestDTO donorRequestDTO) throws Exception;
    DonorGetDTO getDonor(GetDonorRequest infoDonorRequest);
}
