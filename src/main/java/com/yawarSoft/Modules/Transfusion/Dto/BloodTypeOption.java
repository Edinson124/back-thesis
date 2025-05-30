package com.yawarSoft.Modules.Transfusion.Dto;

import lombok.Getter;

@Getter
public class BloodTypeOption {
    private String label;
    private String value;

    public BloodTypeOption(String label, String value) {
        this.label = label;
        this.value = value;
    }
}

