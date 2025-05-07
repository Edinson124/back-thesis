package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequestDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.DonorRequest;

public interface DonorService {
    Long createDonor(DonorRequestDTO donorRequestDTO);
    Boolean existsByDocument(Long id, String documentType, String documentNumber);
    DonorGetDTO updateDonor(DonorRequestDTO donorRequestDTO) throws Exception;
    DonorGetDTO getDonor(String documentType, String documentNumber);
    Long getIdDonor(String documentType, String documentNumber);

    boolean updateDonorReactiveTestSeorologyById(Long id);
    boolean updateDonorBloodType(Long donorId, String bloodType,String rhFactor);
}
