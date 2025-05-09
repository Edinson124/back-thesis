package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitStatus {
    APTO("Apto"),
    RESERVADO("Reservado"),
    QUARANTINED("En cuarentena"),
    REACTIVO("Reactivo");

    private final String label;

    UnitStatus(String label) {
        this.label = label;
    }

}