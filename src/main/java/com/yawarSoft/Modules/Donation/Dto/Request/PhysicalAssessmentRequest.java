package com.yawarSoft.Modules.Donation.Dto.Request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalAssessmentRequest {

    private Long donationId;
    private BigDecimal weight;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private BigDecimal temperature;
    private Integer heartRate;
    private String bloodType;
    private String rhFactor;
    private BigDecimal hemoglobin;
    private BigDecimal hematocrit;
    private BigDecimal leukocytes;
    private BigDecimal monocytes;
    private Integer platelets;
    private String observation;
}
