package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transfusion_assignments")
public class TransfusionAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_transfusion_request", nullable = false)
    private TransfusionRequestEntity transfusionRequest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_unit", nullable = false)
    private UnitEntity bloodUnit;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "return_reason")
    private String returnReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private UserEntity receivedBy;

    @Column(name = "crossmatch_result", length = 20)
    private String crossmatchResult;

    @Column(name = "crossmatch_test_date")
    private LocalDateTime crossmatchTestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_test_by")
    private UserEntity performedTestBy;

    @Column(name = "observation_test")
    private String observationTest;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "dispensed_date")
    private LocalDateTime dispensedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispensed_by")
    private UserEntity dispensedBy;
}
