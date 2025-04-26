package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.DonorDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.GetDonorRequest;

public interface DonorService {
    DonorDTO createDonor(DonorDTO donorDTO);
    DonorGetDTO getDonor(GetDonorRequest infoDonorRequest);
}
