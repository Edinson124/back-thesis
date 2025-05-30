package com.yawarSoft.Modules.Transfusion.Dto.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {

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
    private String occupation;
    private String bloodType;
    private String rhFactor;

    // PatientEntity fields
    private String allergic;
    private Integer numberBirths;
}
