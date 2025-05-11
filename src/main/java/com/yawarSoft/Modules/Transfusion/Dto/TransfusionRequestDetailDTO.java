package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusionRequestDetailDTO {
    private Long id;
    private String unitType;
    private Integer requestedQuantity;
}
