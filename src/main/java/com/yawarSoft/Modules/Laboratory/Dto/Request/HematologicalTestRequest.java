package com.yawarSoft.Modules.Laboratory.Dto.Request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HematologicalTestRequest {

    private Long donationId;
    private LocalDate testDate;
    private String bloodType;
    private String rhFactor;
    private String phenotype;
    private String genotype;
    private String irregularAntibodies;
    private BigDecimal hemoglobin;
    private BigDecimal hematocrit;
    private Integer platelets;
    private BigDecimal leukocytes;
    private BigDecimal monocytes;
    private String observations;
}
