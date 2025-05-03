package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationViewDTO {

    private Long id;
    private Integer bloodBankId;
    private String donorDocumentType;
    private String donorDocumentNumber;
    private String donorFullName;
    private String patientDocumentType;
    private String patientDocumentNumber;
    private String patientFullName;
    private String donationPurpose;
    private String bloodComponent;
    private String observation;
    private LocalDate date;
    private String status;
    private Boolean interrupted;
    private String interruptionPhase;
    private String deferralType;
    private String deferralReason;
    private Integer deferralDuration;


    private Long physicalAssessmentId;
    private Long interviewAnswerId;
    private Long bloodExtractionId;

    //Auditoria
    private Integer createdByIdBloodExtraction;
    private String createdByNameBloodExtraction;
    private LocalDateTime createdAtBloodExtraction;
    private Integer updatedByIdBloodExtraction;
    private String updatedByNameBloodExtraction;
    private LocalDateTime updatedAtBloodExtraction;

    //Auditoria
    private Integer createdByIdInterviewAnswer;
    private String createdByNameInterviewAnswer;
    private LocalDateTime createdAtInterviewAnswer;

    //Auditoria
    private Integer createdByIdPhysicalAssessment;
    private String createdByNamePhysicalAssessment;
    private LocalDateTime createdAtPhysicalAssessment;
    private Integer updatedByIdPhysicalAssessment;
    private String updatedByNamePhysicalAssessment;
    private LocalDateTime updatedAtPhysicalAssessment;

    //Auditoria
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;

}