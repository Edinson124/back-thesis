package com.yawarSoft.Modules.Transfusion.Enums;

import lombok.Getter;

@Getter
public enum TransfusionStatus {
    PENDIENTE("Pendiente"),
    ACEPTADA("Aceptada"),
    LIBERADA("Liberada"),
    FINALIZADA("Finalizada"),
    NO_ATENDIDA("No atendida");

    private final String label;

    TransfusionStatus(String label) {
        this.label = label;
    }

}
