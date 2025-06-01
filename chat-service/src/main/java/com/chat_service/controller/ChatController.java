// ChatController.java
package com.chat_service.controller;

import com.chat_service.dto.*;
import com.chat_service.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Crear nueva sala de chat (Cliente)
    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@Valid @RequestBody CreateChatRoomRequest request) {
        ChatRoomDTO chatRoom = chatService.createChatRoom(request);
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }

    // Asignar agente a chat (Agente)
    @PutMapping("/rooms/assign-agent")
    public ResponseEntity<ChatRoomDTO> assignAgent(@Valid @RequestBody AssignAgentRequest request) {
        ChatRoomDTO chatRoom = chatService.assignAgent(request);
        return ResponseEntity.ok(chatRoom);
    }

    // Enviar mensaje
    @PostMapping("/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        ChatMessageDTO message = chatService.sendMessage(request);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Cerrar chat
    @PutMapping("/rooms/{chatRoomId}/close")
    public ResponseEntity<ChatRoomDTO> closeChat(
            @PathVariable Long chatRoomId,
            @RequestParam String closedBy) {
        ChatRoomDTO chatRoom = chatService.closeChat(chatRoomId, closedBy);
        return ResponseEntity.ok(chatRoom);
    }

    // Obtener chats en espera (Panel de Agentes)
    @GetMapping("/rooms/waiting")
    public ResponseEntity<List<ChatRoomDTO>> getWaitingChats() {
        List<ChatRoomDTO> waitingChats = chatService.getWaitingChats();
        return ResponseEntity.ok(waitingChats);
    }

    // Obtener chats activos del agente
    @GetMapping("/rooms/agent/{agentId}/active")
    public ResponseEntity<List<ChatRoomDTO>> getAgentActiveChats(@PathVariable String agentId) {
        List<ChatRoomDTO> activeChats = chatService.getAgentActiveChats(agentId);
        return ResponseEntity.ok(activeChats);
    }

    // Obtener historial de chats del cliente
    @GetMapping("/rooms/customer/{customerId}/history")
    public ResponseEntity<List<ChatRoomDTO>> getCustomerChatHistory(@PathVariable String customerId) {
        List<ChatRoomDTO> chatHistory = chatService.getCustomerChatHistory(customerId);
        return ResponseEntity.ok(chatHistory);
    }

    // Obtener mensajes de un chat específico
    @GetMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable Long chatRoomId) {
        List<ChatMessageDTO> messages = chatService.getChatMessages(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    // Marcar mensajes como leídos
    @PutMapping("/rooms/{chatRoomId}/read")
    public ResponseEntity<Void> markMessagesAsRead(
            @PathVariable Long chatRoomId,
            @RequestParam String userId) {
        chatService.markMessagesAsRead(chatRoomId, userId);
        return ResponseEntity.ok().build();
    }

    // Obtener información de una sala de chat específica
    @GetMapping("/rooms/{chatRoomId}")
    public ResponseEntity<ChatRoomDTO> getChatRoom(@PathVariable Long chatRoomId) {
        // Este método necesitaría ser implementado en el servicio
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chat Service is running");
    }
}