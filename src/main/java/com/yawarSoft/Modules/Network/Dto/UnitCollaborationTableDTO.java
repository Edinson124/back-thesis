package com.yawarSoft.Modules.Network.Dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitCollaborationTableDTO {

    private Integer id;
    private String stampPronahebas;
    private Integer donationId;
    private String unitType;
    private String bloodType;
    private String serologyResult;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private String status;
}