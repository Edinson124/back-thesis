package com.yawarSoft.Modules.Transfusion.Enums;

import lombok.Getter;

@Getter
public enum TransfusionAssingmentStatus {
    ACTIVE("Activo");

    private final String label;

    TransfusionAssingmentStatus(String label) {
        this.label = label;
    }

}
