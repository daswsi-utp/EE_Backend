package com.event_service.dto;

import com.event_service.entity.EstadoEvento;
import com.event_service.entity.TipoReunion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotNull(message = "La fecha es obligatoria")
    private String fecha; // Formato: YYYY-MM-DD

    @NotNull(message = "La hora es obligatoria")
    private String hora; // Formato: HH:mm AM/PM

    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private EstadoEvento estado;

    @NotNull(message = "El tipo de reunión es obligatorio")
    private TipoReunion tipoReunion;

    // Constructores
    public EventRequestDTO() {}

    public EventRequestDTO(String titulo, String fecha, String hora, String descripcion,
                           EstadoEvento estado, TipoReunion tipoReunion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipoReunion = tipoReunion;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public TipoReunion getTipoReunion() {
        return tipoReunion;
    }

    public void setTipoReunion(TipoReunion tipoReunion) {
        this.tipoReunion = tipoReunion;
    }
}