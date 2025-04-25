package com.yawarSoft.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "physical_assessment")
public class PhysicalAssessmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "systolic_pressure", nullable = false)
    private Integer systolicPressure;

    @Column(name = "diastolic_pressure", nullable = false)
    private Integer diastolicPressure;

    @Column(name = "temperature", nullable = false, precision = 4, scale = 2)
    private BigDecimal temperature;

    @Column(name = "heart_rate", nullable = false)
    private Integer heartRate;

    @Column(name = "hemoglobin", nullable = false, precision = 4, scale = 2)
    private BigDecimal hemoglobin;

    @Column(name = "hematocrit", nullable = false, precision = 5, scale = 2)
    private BigDecimal hematocrit;

    @Column(name = "observation", columnDefinition = "TEXT")
    private String observation;

    @Column(name = "blood_type", nullable = false, length = 10)
    private String bloodType;

    @Column(name = "rh_factor", nullable = false, length = 10)
    private String rhFactor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private UserEntity updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
