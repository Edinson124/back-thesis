package com.yawarSoft.Modules.Transfusion.Enums;

import lombok.Getter;

@Getter
public enum TransfusionAssingmentStatus {
    PENDING("Pendiente"),
    DISPATCHED ("Liberada"),
    APPROVED("Aprobado"),
    REJECTED("Rechazada");

    private final String label;

    TransfusionAssingmentStatus(String label) {
        this.label = label;
    }

}
