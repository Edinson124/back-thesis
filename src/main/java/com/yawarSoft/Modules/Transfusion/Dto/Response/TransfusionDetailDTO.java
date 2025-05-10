package com.yawarSoft.Modules.Transfusion.Dto.Response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionDetailDTO {

    private Long id;
    private String patientDocumentNumber;
    private String patientName;
    private String patientBloodType;
    private String patientRhFactor;
    private String attendingDoctorName;
    private Long transfusionResultId;

}
