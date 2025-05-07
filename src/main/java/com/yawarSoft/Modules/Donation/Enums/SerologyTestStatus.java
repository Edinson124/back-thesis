package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum SerologyTestStatus {
    REACTIVE("REACTIVO"),
    NO_REACTIVE("NO REACTIVO");

    private final String label;

    SerologyTestStatus(String label) {
        this.label = label;
    }

}
