package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.Request.DispensedAssignUnitRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionAssignResultDTO;
import com.yawarSoft.Modules.Transfusion.Dto.TransfusionAssignmentDTO;
import org.springframework.transaction.annotation.Transactional;

public interface TransfusionAssignmentService {
    TransfusionAssignmentDTO saveTransfusionAssignment(Long idTransfusion, Long idUnit);

    @Transactional
    Long deleteTransfusionAssignment(Long idTransfusionAssignment);

    @Transactional
    TransfusionAssignmentDTO saveValidateResult(Long idTransfusionAssignment, TransfusionAssignResultDTO request);

    @Transactional
    Long dispensedUnits(Long idTransfusion, DispensedAssignUnitRequestDTO requestDTO);
}
