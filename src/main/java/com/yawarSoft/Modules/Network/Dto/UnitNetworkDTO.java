package com.yawarSoft.Modules.Network.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitNetworkDTO {

    private Long id;
    private Integer donationId;
    private String unitType;
    private BigDecimal volume;
    private String bloodType;
    private String anticoagulant;
    private String bagType;
    private LocalDate expirationDate;
    private String status;
}
