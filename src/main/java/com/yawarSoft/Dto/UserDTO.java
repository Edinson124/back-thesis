package com.yawarSoft.Dto;

import com.yawarSoft.Enums.UserStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;
    private String profileImageUrl;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private LocalDate birthDate;
    private String region;
    private String province;
    private String district;
    private UserStatus status;
    private Integer roleId;
    private Integer bloodBankId;
}