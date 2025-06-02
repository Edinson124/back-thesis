package com.yawarSoft.Modules.Donation.Dto.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeferralDonationRequest {
    private String reason;
    private Integer days;
    private String type;
}
