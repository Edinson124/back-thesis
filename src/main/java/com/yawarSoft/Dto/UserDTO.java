package com.yawarSoft.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yawarSoft.Enums.UserStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;
    private String profileImageUrl;
    private String email;
    private String phone;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String region;
    private String province;
    private String district;
    private UserStatus status;
    private String role;
    private String bloodBankId;
}
