package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankListDTO {

    private Integer id;
    private String name;
    private String region;
    private String province;
    private String district;
    private String address;
    private String type;
    private Integer idCoordinator;
    private String fullNameCoordinator;
    private String status;
}
