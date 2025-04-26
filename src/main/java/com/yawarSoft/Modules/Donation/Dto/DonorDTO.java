package com.yawarSoft.Modules.Donation.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonorDTO {

    private String firstName;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;
    private LocalDate birthDate;
    private String gender;
    private String region;
    private String province;
    private String district;
    private String address;
    private String phone;
    private String email;
    private String bloodType;
    private String rhFactor;

    // DonorEntity fields
    private String placeOfBirth;
    private String placeOfOrigin;
    private String maritalStatus;
    private String trips;
    private boolean donationRequest;
}
