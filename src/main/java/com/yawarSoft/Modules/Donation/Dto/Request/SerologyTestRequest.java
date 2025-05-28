package com.yawarSoft.Modules.Donation.Dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SerologyTestRequest {

    private Long donationId;

    private LocalDate testDate;
    private Boolean hiv;
    private Boolean hbsAg;
    private Boolean hbcAb;
    private Boolean hcv;
    private Boolean syphilis;
    private Boolean chagas;

    @JsonProperty("htlvI_II")
    private Boolean htlvI_II;

    private String observations;
}
