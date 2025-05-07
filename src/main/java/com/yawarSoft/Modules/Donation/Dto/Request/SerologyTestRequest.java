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

    @JsonProperty("HIV")
    private Boolean HIV;

    @JsonProperty("HBsAg")
    private Boolean HBsAg;

    @JsonProperty("HBcAb")
    private Boolean HBcAb;

    @JsonProperty("HCV")
    private Boolean HCV;

    @JsonProperty("syphilis")
    private Boolean syphilis;

    @JsonProperty("chagas")
    private Boolean chagas;

    @JsonProperty("htlvI_II")
    private Boolean htlvI_II;

    private String observations;
}
