package com.yawarSoft.Modules.Donation.Dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationByDonorDTO {

    private Long id;
    private String bloodBankName;
    private LocalDateTime date;
    private String status;
}
