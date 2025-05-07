package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum DonorStatus {
    ELIGIBLE("Apto"),
    TEMPORARILY_DEFERRED("Diferido Temporalmente"),
    PERMANENTLY_DEFERRED("Diferido Permanentemente");

    private final String label;

    DonorStatus(String label) {
        this.label = label;
    }

}

