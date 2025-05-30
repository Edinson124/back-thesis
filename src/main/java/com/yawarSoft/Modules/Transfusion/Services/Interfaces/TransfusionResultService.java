package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;

public interface TransfusionResultService {
    Long createTransfusionResult(Long idTransfusion, TransfusionResultRequestDTO request);
}
