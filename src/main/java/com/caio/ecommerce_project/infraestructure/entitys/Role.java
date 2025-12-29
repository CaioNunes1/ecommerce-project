package com.caio.ecommerce_project.infraestructure.entitys;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public boolean isBlank() {
        return false;
    }
}
