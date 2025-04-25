package com.yawarSoft.Core.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bb_networks")
public class NetworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL)
    private List<BloodBankNetworkEntity> bloodBankRelations;

}
