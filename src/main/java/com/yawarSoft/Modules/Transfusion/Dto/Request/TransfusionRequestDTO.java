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

    private Long id;
    private String patientDocumentNumber;
    private String patientDocumentType;
    private String patientName;
    private String patientBloodType;
    private String patientRhFactor;
    private Integer attendingDoctor;
    private String attendingDoctorName;
    private String bed;
    private String medicalService;
    private Boolean hasCrossmatch;
    private String diagnosis;
    private String requestReason;
    private List<TransfusionRequestDetailDTO> request;
}
