package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitStatus {
    DISPONIBLE("DISPONIBLE"),
    QUARANTINED("En cuarentena"),
    REACTIVO("Reactivo");

    private final String label;

    UnitStatus(String label) {
        this.label = label;
    }

}