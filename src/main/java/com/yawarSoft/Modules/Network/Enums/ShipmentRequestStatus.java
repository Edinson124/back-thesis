package com.yawarSoft.Modules.Network.Enums;

import lombok.Getter;

@Getter
public enum ShipmentRequestStatus {
    SUITABLE("Apto"),
    RESERVED("Reservado"),
    QUARANTINED("En cuarentena"),
    FRACTIONATED("Fraccionado"),
    DISCARD("Descartado"),
    REACTIVE("Reactivo");

    private final String label;

    ShipmentRequestStatus(String label) {
        this.label = label;
    }
}
