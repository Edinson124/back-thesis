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

    @Column(name = "received_by_document", length = 20)
    private String receivedByDocument;

    @Column(name = "received_by_name", length = 100)
    private String receivedByName;

    @Column(name = "validate_result", length = 20)
    private String validateResult;

    @Column(name = "validate_result_date")
    private LocalDateTime validateResultDate;

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
