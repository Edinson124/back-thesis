package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionViewDTO {

    private Long id;
    private String patientDocumentType;
    private String patientDocumentNumber;
    private String patientBloodType;
    private String patientRhFactor;
    private String patientName;

    private String attendingDoctorName;

    private LocalDateTime date;
    private String bed;
    private String medicalService;
    private Boolean hasCrossmatch;
    private String requestReason;
    private String diagnosis;
}
