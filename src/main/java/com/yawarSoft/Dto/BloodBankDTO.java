package com.yawarSoft.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankDTO {

    private Long id;
    private String name;
    private String region;
    private String province;
    private String district;
    private String address;
    private String type;
    private String status;
}
