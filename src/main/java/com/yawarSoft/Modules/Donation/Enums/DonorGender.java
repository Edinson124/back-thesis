package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum DonorGender {
    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private final String label;

    DonorGender(String label) {
        this.label = label;
    }

}

