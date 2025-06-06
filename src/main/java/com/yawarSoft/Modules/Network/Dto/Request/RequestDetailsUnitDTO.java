package com.yawarSoft.Modules.Network.Dto.Request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailsUnitDTO {
    private Integer id;
    private String unitType;
    private Integer requestedQuantity;
    private String bloodGroup;
    private String rhFactor;
}
