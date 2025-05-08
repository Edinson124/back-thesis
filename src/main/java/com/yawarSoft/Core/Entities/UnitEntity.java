package com.yawarSoft.Core.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "units")
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_donation", nullable = false)
    private DonationEntity donation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_bank_actual")
    private BloodBankEntity bloodBank;

    @Column(name = "unit_type", nullable = false, length = 50)
    private String unitType;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal volumen;

    @Column(name = "blood_type", nullable = false, length = 10)
    private String bloodType;

    @Column(name = "rh_factor", nullable = false, length = 10)
    private String rhFactor;

    @Column(name = "anticoagulant")
    private String anticoagulant;

    @Column(name = "bag_type")
    private String bagType;

    @Column(name = "label_url")
    private String labelUrl;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "serology_result")
    private String serologyResult;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private UserEntity updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
