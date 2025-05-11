package com.yawarSoft.Modules.Storage.Enums;

import lombok.Getter;

@Getter
public enum UnitTypes {
    SANGRE_TOTAL("Sangre total","vida.sangre.total"),
    CONCENTRADO_ERITROCITOS("Concentrado de eritrocitos","vida.concentrado.eritrocitos"),
    PLASMA_FRESCO_CONGELADO("Plasma fresco congelado","vida.plasma.fresco.congelado"),
    CRIOPRECIPITADOS("Crioprecipitados","vida.crioprecipitados"),
    PLAQUETAS("Plaquetas","vida.plaquetas"),
    AFERESIS_PLAQUETAS("Aféresis de plaquetas","vida.aferesis.plaquetas"),
    AFERESIS_GLOBULOS_ROJOS("Aféresis de glóbulos rojos","vida.aferesis.globulos.rojos"),
    AFERESIS_PLASMA("Aféresis de plasma","vida.aferesis.plasma"),;

    private final String label;
    private final String lifeTime;

    UnitTypes(String label, String lifeTime) {
        this.label = label;
        this.lifeTime = lifeTime;
    }

    public static String getLifeTimeByLabel(String label) {
        for (UnitTypes type : UnitTypes.values()) {
            if (type.getLabel().equalsIgnoreCase(label)) {
                return type.getLifeTime();
            }
        }
        throw new IllegalArgumentException("Tipo de unidad no válido: " + label);
    }
}
