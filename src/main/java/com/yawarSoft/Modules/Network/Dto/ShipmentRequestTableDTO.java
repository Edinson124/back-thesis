package com.yawarSoft.Modules.Network.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequestTableDTO {
    private Integer id;
    private String bloodBankNameOrigin;
    private String bloodBankNameDestination;
    private String createdByName;
    private LocalDate requestDate;
    private String status;
}
