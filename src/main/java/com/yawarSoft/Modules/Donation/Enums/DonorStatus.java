package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum DonorStatus {
    APTO("Apto"),
    DIFERIDO_TEMPORALMENTE("Diferido Temporalmente"),
    DIFERIDO_PERMANENTEMENTE("Diferido Permanentemente");

    private final String label;

    DonorStatus(String label) {
        this.label = label;
    }

}

