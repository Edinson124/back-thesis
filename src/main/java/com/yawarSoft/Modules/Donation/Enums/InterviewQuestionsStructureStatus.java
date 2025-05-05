package com.yawarSoft.Modules.Donation.Enums;

import lombok.Getter;

@Getter
public enum InterviewQuestionsStructureStatus {
    ACTIVE("Activo"),
    INACTIVE("Inactivo");

    private final String label;

    InterviewQuestionsStructureStatus(String label) {
        this.label = label;
    }

}
