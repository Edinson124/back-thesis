package com.yawarSoft.Modules.Laboratory.Services.Interfaces;

import com.yawarSoft.Modules.Laboratory.Dto.Request.SerologyTestRequest;
import com.yawarSoft.Modules.Laboratory.Dto.SerologyTestDTO;

public interface SerologyTestService {
    Long createSerologyTest(SerologyTestRequest serologyTestRequest);

    SerologyTestDTO getSerologyTest(Long donationId);
}
