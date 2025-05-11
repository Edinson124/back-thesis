package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum SampleStatus {
    REGISTERED("Registrado");

    private final String label;

    SampleStatus(String label) {
        this.label = label;
    }

}
