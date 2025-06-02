package com.yawarSoft.Modules.Laboratory.Services.Interfaces;


import com.yawarSoft.Modules.Laboratory.Dto.HematologicalTestDTO;
import com.yawarSoft.Modules.Laboratory.Dto.Request.HematologicalTestRequest;

public interface HematologicalTestService {
    Long createHematologicalTest(HematologicalTestRequest hematologicalTestRequest);
    HematologicalTestDTO getHematologicalTest(Long donationId);
}
