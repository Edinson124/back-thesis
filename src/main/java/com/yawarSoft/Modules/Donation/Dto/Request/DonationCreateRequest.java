package com.yawarSoft.Modules.Donation.Dto.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationCreateRequest {

        private Integer bloodBankId;
        private Long donorId;
        private Long patientId;
        private String donationPurpose;
        private String bloodComponent;
        private String observation;
}
