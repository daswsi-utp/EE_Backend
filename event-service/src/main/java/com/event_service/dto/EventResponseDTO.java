package com.event_service.dto;

import com.event_service.entity.EstadoEvento;
import com.event_service.entity.TipoReunion;

public class EventResponseDTO {

    private Long id;
    private String titulo;
    private String fecha;
    private String horaFormateada; // Formato 12 horas
    private String descripcion;
    private EstadoEvento estado;
    private TipoReunion tipoReunion;

    // Constructores
    public EventResponseDTO() {}

    public EventResponseDTO(Long id, String titulo, String fecha, String horaFormateada,
                            String descripcion, EstadoEvento estado, TipoReunion tipoReunion) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.horaFormateada = horaFormateada;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipoReunion = tipoReunion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getHoraFormateada() {
        return horaFormateada;
    }

    public void setHoraFormateada(String horaFormateada) {
        this.horaFormateada = horaFormateada;
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