package com.yawarSoft.Modules.Network.Enums;

import lombok.Getter;

@Getter
public enum ShipmentRequestStatus {
    PENDING("Pendiente"),
    SENT("Solicitado"),
    REFUSED("Rechazado"),
    RELEASED("Liberado"),
    COMPLETED("Finalizado");

    private final String label;

    ShipmentRequestStatus(String label) {
        this.label = label;
    }
}
