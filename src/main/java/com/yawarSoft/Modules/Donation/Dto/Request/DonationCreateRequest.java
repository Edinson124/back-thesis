package com.yawarSoft.Modules.Donation.Dto.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationCreateRequest {

        private String documentTypeDonor;
        private String documentNumberDonor;
        private String documentTypePatient;
        private String documentNumberPatient;
        private Integer bloodBankId;
        private String donationPurpose;
        private String bloodComponent;
        private String observation;
}

