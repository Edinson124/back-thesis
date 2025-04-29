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
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private BigDecimal temperature;
    private Integer heartRate;
    private BigDecimal hemoglobin;
    private BigDecimal hematocrit;
    private String observation;
    private String bloodType;
    private String rhFactor;
}
