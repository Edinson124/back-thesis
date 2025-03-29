package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blood_bank_x_network")
public class BloodBankNetworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_network", nullable = false)
    private NetworkEntity network;

    @ManyToOne
    @JoinColumn(name = "id_blood_bank", nullable = false)
    private BloodBankEntity bloodBank;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private UserEntity createdBy;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "disassociated_at")
    private LocalDateTime disassociatedAt;

    @ManyToOne
    @JoinColumn(name = "disassociated_by", referencedColumnName = "id")
    private UserEntity disassociatedBy;

    @Column(name = "status")
    private String status;
}
