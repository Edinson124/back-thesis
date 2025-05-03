package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationResponseDTO {

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

}