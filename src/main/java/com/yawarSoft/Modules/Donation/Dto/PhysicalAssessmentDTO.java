package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalAssessmentDTO {

    private Long id;
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
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;
}
