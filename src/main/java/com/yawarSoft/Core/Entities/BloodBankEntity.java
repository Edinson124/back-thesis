package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blood_banks")
public class BloodBankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String region;
    private String province;
    private String district;
    private String address;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_blood_bank_type", nullable = false)
    private BloodBankTypeEntity bloodBankType;

    @ManyToOne
    @JoinColumn(name = "id_coordinator", nullable = false)
    private UserEntity coordinator;
}