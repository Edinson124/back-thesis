package com.yawarSoft.Modules.Donation.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HematologicalTestDTO {
    private Long id;
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
    private String status;
    private String observations;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private Integer updatedById;
    private String updatedByName;
    private LocalDateTime updatedAt;
}