package com.yawarSoft.Modules.Laboratory.Enums;

import lombok.Getter;

@Getter
public enum SerologyTestStatus {
    REACTIVE("REACTIVO"),
    PENDING("PENDIENTE"),
    NO_REACTIVE("NO REACTIVO");

    private final String label;

    SerologyTestStatus(String label) {
        this.label = label;
    }

}
