package com.event_service.entity;

public enum TipoReunion {
    CLIENTE("Cliente"),
    EMPLEADO("Empleado");

    private final String descripcion;

    TipoReunion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}