package com.yawarSoft.Modules.Donation.Dto.Response;

import com.yawarSoft.Modules.Donation.Dto.DonationViewDTO;
import com.yawarSoft.Modules.Donation.Dto.DonorGetDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationGetDTO {

    private Boolean canViewDonation ;
    private DonorGetDTO donor;
    private DonationViewDTO donation;
}
