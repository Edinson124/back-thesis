package com.yawarSoft.Modules.Storage.Dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    private Long id;
    private Integer donationId;
    private String unitType;
    private BigDecimal volumen;
    private String bloodType;
    private String anticoagulant;
    private String bagType;
    private LocalDate expirationDate;
    private String status;
}
