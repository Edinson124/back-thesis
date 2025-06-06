package com.yawarSoft.Modules.Network.Dto.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequestDTO {
    private Integer idBloodBank;
    private String reason;
    private String details;
    private List<RequestDetailsUnitDTO> units;
}
