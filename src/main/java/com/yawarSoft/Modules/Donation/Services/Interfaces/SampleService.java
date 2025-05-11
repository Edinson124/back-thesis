package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.SampleDTO;

import java.util.List;

public interface SampleService {
    Long createSample(Long idDonation, String test);

    List<SampleDTO> getSamples(Long idDonation);
}
