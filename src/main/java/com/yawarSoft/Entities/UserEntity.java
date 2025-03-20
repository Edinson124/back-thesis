package com.yawarSoft.Entities;

import com.yawarSoft.Enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "second_last_name")
    private String secondLastName;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "document_number")
    private String documentNumber;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    private String email;
    private String phone;
    private String gender;
    private String address;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    private String region;
    private String province;
    private String district;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name ="id_role"))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_blood_bank")
    private BloodBankEntity bloodBank;
}