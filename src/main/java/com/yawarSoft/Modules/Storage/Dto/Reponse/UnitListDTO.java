package com.yawarSoft.Modules.Storage.Dto.Reponse;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitListDTO {

    private Integer id;
    private String unitType;
    private String bloodType;
    private String serologyResult;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private String status;
}
