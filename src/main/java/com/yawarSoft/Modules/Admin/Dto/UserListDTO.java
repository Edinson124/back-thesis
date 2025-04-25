package com.yawarSoft.Modules.Admin.Dto;

import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String secondLastName;
    private String documentType;
    private String documentNumber;
//    private String profileImageUrl;
    private String email;
    private String phone;
//    private String gender;
//    private String address;
//    private LocalDate birthDate;
//    private String region;
//    private String province;
//    private String district;
    private UserStatus status;
    private String role;
    private Integer bloodBankId;
}
