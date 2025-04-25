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
@Table(name = "storage_records")
public class StorageRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_bank", nullable = false)
    private BloodBankEntity bloodBank;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_unit", nullable = false)
    private UnitEntity unit;

    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;

    @Column(name = "record_type", length = 20, nullable = false)
    private String recordType;

    @Column(length = 30, nullable = false)
    private String reason;

    @Column(name = "related_request_type", length = 30)
    private String relatedRequestType;

    @Column(name = "related_request_id")
    private Long relatedRequestId;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
