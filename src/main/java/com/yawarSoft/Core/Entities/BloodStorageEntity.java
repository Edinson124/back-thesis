package com.yawarSoft.Core.Entities;



import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "blood_storage")
public class BloodStorageEntity {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private BloodBankEntity bloodBank;

    @Column(name = "total_blood", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer totalBlood;

    @Column(name = "erythrocyte_concentrate", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer erythrocyteConcentrate;

    @Column(name = "fresh_frozen_plasma", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer freshFrozenPlasma;

    @Column(name = "cryoprecipitate", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer cryoprecipitate;

    @Column(name = "platelet", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer platelet;

    @Column(name = "platelet_apheresis", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer plateletApheresis;

    @Column(name = "red_blood_cells_apheresis", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer redBloodCellsApheresis;

    @Column(name = "plasma_apheresis", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer plasmaApheresis;
}