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
@Table(name = "serology_tests")
public class SerologyTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(name = "HIV", nullable = false)
    private Boolean HIV;

    @Column(name = "HBsAg", nullable = false)
    private Boolean HBsAg;

    @Column(name = "HbCAb", nullable = false)
    private Boolean HbCAb;

    @Column(name = "HCV", nullable = false)
    private Boolean HCV;

    @Column(name = "Syphilis", nullable = false)
    private Boolean syphilis;

    @Column(name = "Chagas", nullable = false)
    private Boolean chagas;

    @Column(name = "HTLV_I_II", nullable = false)
    private Boolean htlvI_II;

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
