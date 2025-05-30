package com.yawarSoft.Modules.Transfusion.Enums;

public enum RhFactor {
    POSITIVO("+"),
    NEGATIVO("-");

    private final String symbol;

    RhFactor(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String getSymbolByName(String name) {
        if (name == null) return "";
        try {
            return RhFactor.valueOf(name.toUpperCase()).getSymbol();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}

