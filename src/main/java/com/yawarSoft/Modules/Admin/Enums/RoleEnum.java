package com.yawarSoft.Modules.Admin.Enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMINISTRADOR(1, "ADMIN"),
    MEDICO_BANCO_DE_SANGRE(2, "Medico de banco de sangre"),
    TECNOLOGO(3, "Tecnologo"),
    TECNICO(4, "Tecnico"),
    MEDICO(5, "Medico solicitante");

    private final int id;
    private final String name;

    RoleEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RoleEnum fromId(int id) {
        for (RoleEnum role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Rol no encontrado para el ID: " + id);
    }

    public static RoleEnum fromName(String name) {
        for (RoleEnum role : values()) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Rol no encontrado para el nombre: " + name);
    }
}
