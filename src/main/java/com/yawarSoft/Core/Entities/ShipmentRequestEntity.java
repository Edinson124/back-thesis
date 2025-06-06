package com.yawarSoft.Core.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipment_requests")
public class ShipmentRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_origin_bank", nullable = false)
    private BloodBankEntity originBank;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_destination_bank", nullable = false)
    private BloodBankEntity destinationBank;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(length = 20, nullable = false)
    private String status;

    @Column
    private String reason;

    @Column
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_accepted_by")
    private UserEntity releaseAcceptedBy;

    @Column(name = "release_accepted_date")
    private LocalDateTime releaseAcceptedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private UserEntity receivedBy;

    @Column(name = "received_date")
    private LocalDateTime receivedDate;

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

    @OneToMany(mappedBy = "shipmentRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentRequestDetailEntity> unitsRequest;

    @OneToMany(mappedBy = "shipmentRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentXUnitEntity> unitsAssigned;
}
