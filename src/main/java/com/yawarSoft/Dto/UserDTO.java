package com.yawarSoft.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstNames;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
    private String status;
    private String role;
}
