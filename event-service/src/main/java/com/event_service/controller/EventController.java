package com.event_service.controller;

import com.event_service.dto.EventRequestDTO;
import com.event_service.dto.EventResponseDTO;
import com.event_service.entity.EstadoEvento;
import com.event_service.entity.TipoReunion;
import com.event_service.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // Crear evento
    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequestDTO requestDTO) {
        try {
            EventResponseDTO response = eventService.createEvent(requestDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al crear evento", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // Obtener todos los eventos
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        List<EventResponseDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Obtener evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            EventResponseDTO event = eventService.getEventById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Evento no encontrado", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    // Actualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequestDTO requestDTO) {
        try {
            EventResponseDTO response = eventService.updateEvent(id, requestDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al actualizar evento", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // Eliminar evento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(
                    new SuccessResponse("Evento eliminado exitosamente"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al eliminar evento", e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    // Obtener eventos por tipo de reunión
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByTipo(@PathVariable TipoReunion tipo) {
        List<EventResponseDTO> events = eventService.getEventsByTipo(tipo);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Obtener eventos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByEstado(@PathVariable EstadoEvento estado) {
        List<EventResponseDTO> events = eventService.getEventsByEstado(estado);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Clases internas para respuestas
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}