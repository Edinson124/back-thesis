package com.yawarSoft.Modules.Admin.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankDTO {

    private Integer id;
    private String name;
    private String region;
    private String province;
    private String district;
    private String address;
    private String profileImageUrl;
    private Integer idType;
    private Integer idCoordinator;
    private String status;
    private Boolean isInternal;
}
