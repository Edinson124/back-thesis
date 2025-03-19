package com.yawarSoft.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    private String description;

    @Column(name = "type_blood_bank")
    private String typeBloodBank;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "id_role"), inverseJoinColumns = @JoinColumn(name ="id_permission"))
    private Set<PermissionEntity> permissionList = new HashSet<>();

}