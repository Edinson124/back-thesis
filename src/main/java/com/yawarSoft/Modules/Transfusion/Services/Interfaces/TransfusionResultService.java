package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequest;

public interface TransfusionResultService {
    Long createTransfusionResult(Long idTransfusion, TransfusionResultRequest request);
}
