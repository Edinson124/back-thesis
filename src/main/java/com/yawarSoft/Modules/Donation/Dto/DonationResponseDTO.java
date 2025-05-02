package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationResponseDTO {

    private Long id;
    private Integer bloodBankId;
    private Long donorId;
    private Long patientId;
    private String donationPurpose;
    private String bloodComponent;
    private String observation;
    private LocalDate date;
    private String status;
    private Boolean interrupted;
}