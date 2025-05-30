package com.yawarSoft.Modules.Transfusion.Enums;

import lombok.Getter;

@Getter
public enum TransfusionAssingmentResult {
    COMPATIBLE("COMPATIBLE"),
    INCOMPATIBLE("INCOMPATIBLE");

    private final String label;

    TransfusionAssingmentResult(String label) {
        this.label = label;
    }

}

