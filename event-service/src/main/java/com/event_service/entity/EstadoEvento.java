package com.event_service.entity;

public enum EstadoEvento {
    PROGRAMADO("Programado"),
    EN_PROGRESO("En Progreso"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado"),
    REPROGRAMADO("Reprogramado");

    private final String descripcion;

    EstadoEvento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}