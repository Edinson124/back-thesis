package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionResultRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.TransfusionResultCreateDTO;

public interface TransfusionResultService {
    TransfusionResultCreateDTO createTransfusionResult(Long idTransfusion, TransfusionResultRequestDTO request);
}
