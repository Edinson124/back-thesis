package com.yawarSoft.Modules.Donation.Dto.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateDonationDTO {

    private Long donationId;
    private LocalDate dateDonation;
    private LocalDate dateEnabledDonation;
    private Boolean isEnableDonation;
}
