package com.yawarSoft.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transfusion_results")
public class TransfusionResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_patient", nullable = false)
    private PatientEntity patient;

    @Column(name = "transfusion_date", nullable = false)
    private LocalDateTime transfusionDate;

    @Column(name = "transfusion_by_name")
    private String transfusionByName;

    @Column(name = "transfusion_by_document_number")
    private String transfusionByLicenseNumber;

    @Column(name = "has_reaction")
    private Boolean hasReaction;

    @Column(name = "reaction_adverse")
    private String reactionAdverse;

    @Column(name = "observations")
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
