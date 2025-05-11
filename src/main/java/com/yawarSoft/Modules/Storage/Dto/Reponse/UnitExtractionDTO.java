package com.yawarSoft.Modules.Storage.Dto.Reponse;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitExtractionDTO {

    private Long id;
    private String type;
    private String bag;
    private BigDecimal volume;
    private String anticoagulant;
}
