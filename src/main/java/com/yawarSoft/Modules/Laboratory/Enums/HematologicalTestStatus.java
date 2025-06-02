package com.yawarSoft.Modules.Laboratory.Enums;

import lombok.Getter;

@Getter
public enum HematologicalTestStatus {
    COMPLETED("Completada");

    private final String label;

    HematologicalTestStatus(String label) {
        this.label = label;
    }

}
