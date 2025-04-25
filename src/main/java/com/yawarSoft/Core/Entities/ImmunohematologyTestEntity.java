package com.yawarSoft.Core.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "immunohematology_tests")
public class ImmunohematologyTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "blood_type", nullable = false, length = 10)
    private String bloodType;

    @Column(name = "rh_factor", nullable = false, length = 10)
    private String rhFactor;

    @Column(name = "antibody_screening", columnDefinition = "TEXT")
    private String antibodyScreening;

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
