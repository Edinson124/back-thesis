package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitTypes {
    SANGRE_TOTAL("Sangre Total"),
    CONCENTRADO_ERITROCITOS("Concentrado de eritrocitos"),
    PLASMA_FRESCO_CONGELADO("Plasma Fresco Congelado"),
    CRIOPRECIPITADOS("Crioprecipitados"),
    PLAQUETAS("Plaquetas"),
    AFERESIS_PLAQUETAS("Aféresis de Plaquetas"),
    AFERESIS_GLOBULOS_ROJOS("Aféresis de Glóbulos Rojos"),
    AFERESIS_PLASMA("Aféresis de Plasma");

    private final String label;

    UnitTypes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
