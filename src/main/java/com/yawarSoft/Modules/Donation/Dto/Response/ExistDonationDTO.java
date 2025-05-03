package com.yawarSoft.Modules.Donation.Dto.Response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExistDonationDTO {

    private Boolean donationActualExists;
    private Boolean canViewDonation ;
    private Long donationId;
    private Boolean existDonor;
}
