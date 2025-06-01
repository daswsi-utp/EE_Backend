package com.event_service.repository;

import com.event_service.entity.Event;
import com.event_service.entity.EstadoEvento;
import com.event_service.entity.TipoReunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Buscar eventos por tipo de reunión
    List<Event> findByTipoReunion(TipoReunion tipoReunion);

    // Buscar eventos por estado
    List<Event> findByEstado(EstadoEvento estado);

    // Buscar eventos por fecha
    List<Event> findByFecha(LocalDate fecha);

    // Buscar eventos por rango de fechas
    List<Event> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar eventos por título (contiene)
    List<Event> findByTituloContainingIgnoreCase(String titulo);

    // Buscar eventos próximos por tipo de reunión
    @Query("SELECT e FROM Event e WHERE e.tipoReunion = :tipo AND e.fecha >= :fecha ORDER BY e.fecha ASC, e.hora ASC")
    List<Event> findUpcomingEventsByTipo(@Param("tipo") TipoReunion tipo, @Param("fecha") LocalDate fecha);

    // Contar eventos por estado
    long countByEstado(EstadoEvento estado);
}