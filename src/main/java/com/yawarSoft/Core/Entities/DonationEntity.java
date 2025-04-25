package com.yawarSoft.Core.Entities;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "donations")
public class DonationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_bank", nullable = false)
    private BloodBankEntity bloodBank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_physical_assessment")
    private PhysicalAssessmentEntity physicalAssessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_interview_answer")
    private InterviewAnswerEntity interviewAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_blood_extraction")
    private BloodExtractionEntity bloodExtraction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inmuno_test")
    private ImmunohematologyTestEntity inmunoTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serology_test")
    private SerologyTestEntity serologyTest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_donor", nullable = false)
    private DonorEntity donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @Column(name = "donation_purpose", nullable = false)
    private String donationPurpose;

    @Column(name = "blood_component", nullable = false)
    private String bloodComponent;

    @Column
    private String observation;

    @Column(nullable = false)
    private boolean interrupted;

    @Column(name = "interruption_phase")
    private String interruptionPhase;

    @Column(name = "deferral_type")
    private String deferralType;

    @Column(name = "deferral_duration")
    private Integer deferralDuration;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
