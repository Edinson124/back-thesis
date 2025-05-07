package com.yawarSoft.Modules.Donation.Services.Interfaces;

import com.yawarSoft.Modules.Donation.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Donation.Dto.SerologyTestDTO;

public interface SerologyTestService {
    Long createSerologyTest(SerologyTestRequest serologyTestRequest);

    SerologyTestDTO getSerologyTest(Long donationId);
}
