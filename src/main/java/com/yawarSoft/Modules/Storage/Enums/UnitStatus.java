package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitStatus {
    SUITABLE("Apto"),
    RESERVED("Reservado"),
    QUARANTINED("En cuarentena"),
    FRACTIONATED("Fraccionado"),
    DISCARD("Descartado"),
    REACTIVE("Reactivo");

    private final String label;

    UnitStatus(String label) {
        this.label = label;
    }

}