package com.yawarSoft.Modules.Transfusion.Dto.Request;

import com.yawarSoft.Modules.Transfusion.Dto.TransfusionRequestDetailDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionRequestDTO {

    private String documentNumberPatient;
    private String documentTypePatient;
    private Integer attendingDoctor;
    private String bed;
    private String medicalService;
    private Boolean hasCrossmatch;
    private String diagnosis;
    private String requestReason;
    private List<TransfusionRequestDetailDTO> requestedUnits;
}
