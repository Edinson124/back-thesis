package com.yawarSoft.Core.Entities;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auth_external_system")
public class AuthExternalSystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_blood_bank", nullable = false)
    private BloodBankEntity bloodBank;

    @Column(name = "description")
    private String description;

    @Column(name = "client_user", nullable = false, unique = true)
    private String clientUser;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "auth_x_authorities",
            joinColumns = @JoinColumn(name = "id_auth_external_sytem"),
            inverseJoinColumns = @JoinColumn(name = "id_authority")
    )
    private List<AuthoritiesExternalSystemEntity> authorities;
}
