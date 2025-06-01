package com.event_service.service;

import com.event_service.dto.EventRequestDTO;
import com.event_service.dto.EventResponseDTO;
import com.event_service.entity.Event;
import com.event_service.entity.EstadoEvento;
import com.event_service.entity.TipoReunion;
import com.event_service.repository.EventRepository;
import com.event_service.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Crear evento
    public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
        try {
            LocalDate fecha = DateTimeUtil.parseDate(requestDTO.getFecha());
            LocalTime hora = DateTimeUtil.parseTime(requestDTO.getHora());

            Event event = new Event();
            event.setTitulo(requestDTO.getTitulo());
            event.setFecha(fecha);
            event.setHora(hora);
            event.setDescripcion(requestDTO.getDescripcion());
            event.setEstado(requestDTO.getEstado());
            event.setTipoReunion(requestDTO.getTipoReunion());

            Event savedEvent = eventRepository.save(event);
            return convertToResponseDTO(savedEvent);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el evento: " + e.getMessage());
        }
    }

    // Obtener todos los eventos
    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener evento por ID
    public EventResponseDTO getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return convertToResponseDTO(event.get());
        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
    }

    // Actualizar evento
    public EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            try {
                Event event = existingEvent.get();
                LocalDate fecha = DateTimeUtil.parseDate(requestDTO.getFecha());
                LocalTime hora = DateTimeUtil.parseTime(requestDTO.getHora());

                event.setTitulo(requestDTO.getTitulo());
                event.setFecha(fecha);
                event.setHora(hora);
                event.setDescripcion(requestDTO.getDescripcion());
                event.setEstado(requestDTO.getEstado());
                event.setTipoReunion(requestDTO.getTipoReunion());

                Event updatedEvent = eventRepository.save(event);
                return convertToResponseDTO(updatedEvent);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar el evento: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
    }

    // Eliminar evento
    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
    }

    // Obtener eventos por tipo de reunión
    public List<EventResponseDTO> getEventsByTipo(TipoReunion tipo) {
        List<Event> events = eventRepository.findByTipoReunion(tipo);
        return events.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener eventos por estado
    public List<EventResponseDTO> getEventsByEstado(EstadoEvento estado) {
        List<Event> events = eventRepository.findByEstado(estado);
        return events.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Convertir Entity a ResponseDTO
    private EventResponseDTO convertToResponseDTO(Event event) {
        String fechaFormateada = DateTimeUtil.formatDate(event.getFecha());
        String horaFormateada = DateTimeUtil.formatTimeToAmPm(event.getHora());

        return new EventResponseDTO(
                event.getId(),
                event.getTitulo(),
                fechaFormateada,
                horaFormateada,
                event.getDescripcion(),
                event.getEstado(),
                event.getTipoReunion()
        );
    }
}