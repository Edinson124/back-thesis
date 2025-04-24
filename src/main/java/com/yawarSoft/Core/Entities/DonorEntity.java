package com.yawarSoft.Entities;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "donors")
public class DonorEntity extends PersonEntity {

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "place_of_origin")
    private String placeOfOrigin;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column
    private String trips;

    @Column(nullable = false)
    private String status;

    @Column(name = "donation_request", nullable = false)
    private boolean donationRequest;

    @Column(name = "deferral_end_date")
    private LocalDate deferralEndDate;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
