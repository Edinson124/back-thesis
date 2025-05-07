package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum DonationStatus {
    IN_PROCRESS("En proceso"),
    FINISHED_TEMP_DEFER("Finalizada con diferimiento temporal"),
    FINISHED_PERM_DEFER("Finalizada con diferimiento permanente"),
    FINISHED("Finalizado");

    private final String label;

    DonationStatus(String label) {
        this.label = label;
    }

}