package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitStatus {
    APTO("Apto"),
    QUARANTINED("En cuarentena");

    private final String label;

    UnitStatus(String label) {
        this.label = label;
    }

}