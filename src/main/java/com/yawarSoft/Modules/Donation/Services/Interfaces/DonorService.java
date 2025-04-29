package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;

public interface DonorService {
    Long createDonor(DonorRequestDTO donorRequestDTO);
    DonorGetDTO updateDonor(Long id, DonorRequestDTO donorRequestDTO) throws Exception;
    DonorGetDTO getDonor(GetDonorRequest infoDonorRequest);
}
