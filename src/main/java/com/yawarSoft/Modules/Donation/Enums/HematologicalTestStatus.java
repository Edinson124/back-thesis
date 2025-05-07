package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum HematologicalTestStatus {
    COMPLETED("Completada");

    private final String label;

    HematologicalTestStatus(String label) {
        this.label = label;
    }

}
