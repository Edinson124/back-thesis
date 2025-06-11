package com.yawarSoft.Modules.Network.Dto;

import com.yawarSoft.Modules.Network.Dto.Request.ShipmentRequestDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentXUnitDTO {
    private Integer id;
    private String stampPronahebas;
    private Long idUnit;
    private String bloodType;
    private String unitType;
    private LocalDate expirationDate;
}
