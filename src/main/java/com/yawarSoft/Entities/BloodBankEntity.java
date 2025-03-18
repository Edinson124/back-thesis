package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blood_bank")
public class BloodBankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String region;
    private String province;
    private String district;
    private String address;
    private String type;
    private String status;
//
//    @ManyToOne
//    @JoinColumn(name = "id_coordinator", nullable = false)
//    private Coordinator coordinator;
}