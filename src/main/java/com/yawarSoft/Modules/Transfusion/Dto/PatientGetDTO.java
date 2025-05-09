package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientGetDTO {

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
    private String allergic;

    //Auditoria
    private Integer createdById;
    private String createdByName;
    private Integer updatedById;
    private String updatedByName;

}