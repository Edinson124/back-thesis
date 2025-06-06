package com.yawarSoft.Modules.Network.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRequestDataDTO {
    private Integer id;
    private String reason;
    private String details;
    private String status;
    private LocalDateTime requestDate;
}
