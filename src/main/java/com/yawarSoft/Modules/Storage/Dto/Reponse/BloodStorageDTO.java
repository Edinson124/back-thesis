package com.yawarSoft.Modules.Storage.Dto.Reponse;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodStorageDTO {

    private Integer totalBlood;
    private Integer erythrocyteConcentrate;
    private Integer freshFrozenPlasma;
    private Integer cryoprecipitate;
    private Integer platelet;
    private Integer plateletApheresis;
    private Integer redBloodCellsApheresis;
    private Integer plasmaApheresis;
}
