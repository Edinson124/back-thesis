package com.yawarSoft.Modules.Transfusion.Services.Interfaces;

import com.yawarSoft.Modules.Transfusion.Dto.Request.TransfusionRequestDTO;
import com.yawarSoft.Modules.Transfusion.Dto.Response.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TransfusionRequestService {
    Page<TransfusionByPatientDTO> getTranfusionByPatient(String documentType, String documentNumber, int page, int size);

    Page<TranfusionListDTO> getTransfusions(int page, int size, LocalDate startEntryDate, LocalDate endEntryDate, Long code, String status);

    ExistTransfusionDTO existsByCode(Long id);

    TransfusionDetailDTO getDetailTransfusion(Long id);

    TransfusionGetDTO getTranfusion(Long id);

    Long createTransfusion(TransfusionRequestDTO transfusionRequestDTO);

    Long editTransfusion(TransfusionRequestDTO transfusionRequestDTO);
}
