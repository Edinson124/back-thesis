package com.yawarSoft.Modules.Admin.Dto.Reponse;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankOptionsAddNetworkDTO {
    private Integer id;
    private String name;
    private String region;
    private String province;
    private String district;
    private Boolean isInternal;
}
