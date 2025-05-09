package com.yawarSoft.Modules.Transfusion.Enums;

import lombok.Getter;

@Getter
public enum TransfusionStatus {
    PENDIENTE("Pendiente"),
    ACEPTADA("Aceptada"),
    FINALIZADA("Finalizada"),
    NO_ATENDIDA("No atendida");

    private final String label;

    TransfusionStatus(String label) {
        this.label = label;
    }

}
