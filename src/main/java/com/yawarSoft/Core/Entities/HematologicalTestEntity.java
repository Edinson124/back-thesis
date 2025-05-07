package com.yawarSoft.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hematological_tests")
public class HematologicalTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "blood_type", nullable = false, length = 10)
    private String bloodType;

    @Column(name = "rh_factor", nullable = false, length = 10)
    private String rhFactor;

    @Column(name = "phenotype", length = 50, nullable = false)
    private String phenotype;

    @Column(name = "genotype", length = 50, nullable = false)
    private String genotype;

    @Column(name = "irregular_antibodies", columnDefinition = "TEXT", nullable = false)
    private String irregularAntibodies;

    @Column(name = "hemoglobin", precision = 4, scale = 2, nullable = false)
    private BigDecimal hemoglobin;

    @Column(name = "hematocrit", precision = 5, scale = 2, nullable = false)
    private BigDecimal hematocrit;

    @Column(name = "platelets")
    private Integer platelets;

    @Column(name = "leukocytes", precision = 4, scale = 2)
    private BigDecimal leukocytes;

    @Column(name = "monocytes", precision = 4, scale = 2)
    private BigDecimal monocytes;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(nullable = false, length = 20)
    private String status;

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
