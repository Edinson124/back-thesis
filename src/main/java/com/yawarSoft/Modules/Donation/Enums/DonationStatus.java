package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum DonationStatus {
    IN_PROCRESS("En proceso");

    private final String label;

    DonationStatus(String label) {
        this.label = label;
    }

}
