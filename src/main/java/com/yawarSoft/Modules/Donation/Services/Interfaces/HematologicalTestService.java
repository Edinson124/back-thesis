package com.yawarSoft.Modules.Donation.Services.Interfaces;


import com.yawarSoft.Modules.Donation.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Donation.Dto.Request.HematologicalTestRequest;

public interface HematologicalTestService {
    Long createHematologicalTest(HematologicalTestRequest hematologicalTestRequest);
    HematologicalTestDTO getHematologicalTest(Long donationId);
}
