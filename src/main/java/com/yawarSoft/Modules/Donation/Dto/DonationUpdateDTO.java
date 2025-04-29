package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationUpdateDTO {

    private Long patientId;
    private String donationPurpose;
    private String bloodComponent;
    private String observation;
}