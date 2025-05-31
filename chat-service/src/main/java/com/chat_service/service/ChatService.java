package com.chat_service.service;

import com.chat_service.dto.*;
import com.chat_service.entity.*;
import com.chat_service.repository.ChatMessageRepository;
import com.chat_service.repository.ChatRoomRepository;
import com.chat_service.exception.ChatRoomNotFoundException;
import com.chat_service.exception.ChatRoomAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Crear nueva sala de chat
    public ChatRoomDTO createChatRoom(CreateChatRoomRequest request) {
        // Verificar si el cliente ya tiene un chat activo
        var existingChat = chatRoomRepository.findByCustomerIdAndStatus(
                request.getCustomerId(), ChatStatus.ACTIVE);

        if (existingChat.isPresent()) {
            throw new ChatRoomAlreadyExistsException("Customer already has an active chat");
        }

        // Verificar si hay un chat en espera
        var waitingChat = chatRoomRepository.findByCustomerIdAndStatus(
                request.getCustomerId(), ChatStatus.WAITING);

        if (waitingChat.isPresent()) {
            return convertToDTO(waitingChat.get());
        }

        // Crear nueva sala de chat
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setCustomerId(request.getCustomerId());
        chatRoom.setCustomerName(request.getCustomerName());
        chatRoom.setCustomerEmail(request.getCustomerEmail());
        chatRoom.setStatus(ChatStatus.WAITING);

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // Notificar a los agentes sobre nuevo chat en espera
        messagingTemplate.convertAndSend("/topic/waiting-chats", convertToDTO(savedChatRoom));

        return convertToDTO(savedChatRoom);
    }

    // Asignar agente a chat
    public ChatRoomDTO assignAgent(AssignAgentRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found"));

        if (chatRoom.getStatus() != ChatStatus.WAITING) {
            throw new IllegalStateException("Chat room is not waiting for agent");
        }

        chatRoom.setAgentId(request.getAgentId());
        chatRoom.setAgentName(request.getAgentName());
        chatRoom.setStatus(ChatStatus.ACTIVE);

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // Notificar al cliente que el agente se ha conectado
        messagingTemplate.convertAndSend(
                "/topic/chat/" + chatRoom.getId(),
                "Agent " + request.getAgentName() + " has joined the chat"
        );

        return convertToDTO(savedChatRoom);
    }

    // Enviar mensaje
    public ChatMessageDTO sendMessage(SendMessageRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found"));

        if (chatRoom.getStatus() == ChatStatus.CLOSED) {
            throw new IllegalStateException("Cannot send message to closed chat");
        }

        // Determinar tipo de sender
        SenderType senderType = chatRoom.getCustomerId().equals(request.getSenderId())
                ? SenderType.CUSTOMER : SenderType.AGENT;

        ChatMessage message = new ChatMessage();
        message.setChatRoom(chatRoom);
        message.setSenderId(request.getSenderId());
        message.setSenderName(request.getSenderName());
        message.setSenderType(senderType);
        message.setContent(request.getContent());
        message.setMessageType(request.getMessageType());

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Enviar mensaje via WebSocket
        ChatMessageDTO messageDTO = convertToDTO(savedMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoom.getId(), messageDTO);

        // Notificar sobre mensaje no leído
        String recipient = senderType == SenderType.CUSTOMER ? chatRoom.getAgentId() : chatRoom.getCustomerId();
        if (recipient != null) {
            messagingTemplate.convertAndSendToUser(recipient, "/queue/notifications", messageDTO);
        }

        return messageDTO;
    }

    // Cerrar chat
    public ChatRoomDTO closeChat(Long chatRoomId, String closedBy) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found"));

        chatRoom.setStatus(ChatStatus.CLOSED);
        chatRoom.setClosedAt(LocalDateTime.now());

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // Notificar cierre del chat
        messagingTemplate.convertAndSend(
                "/topic/chat/" + chatRoomId,
                "Chat has been closed by " + closedBy
        );

        return convertToDTO(savedChatRoom);
    }

    // Obtener chats en espera
    @Transactional(readOnly = true)
    public List<ChatRoomDTO> getWaitingChats() {
        List<ChatRoom> waitingChats = chatRoomRepository.findByStatusOrderByCreatedAtAsc(ChatStatus.WAITING);
        return waitingChats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener chats activos del agente
    @Transactional(readOnly = true)
    public List<ChatRoomDTO> getAgentActiveChats(String agentId) {
        List<ChatRoom> activeChats = chatRoomRepository.findActiveChatsByAgent(agentId);
        return activeChats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener historial de chats del cliente
    @Transactional(readOnly = true)
    public List<ChatRoomDTO> getCustomerChatHistory(String customerId) {
        List<ChatRoom> chatHistory = chatRoomRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        return chatHistory.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener mensajes del chat
    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getChatMessages(Long chatRoomId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Marcar mensajes como leídos
    public void markMessagesAsRead(Long chatRoomId, String userId) {
        chatMessageRepository.markMessagesAsRead(chatRoomId, userId);
    }

    // Métodos de conversión
    private ChatRoomDTO convertToDTO(ChatRoom chatRoom) {
        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setId(chatRoom.getId());
        dto.setCustomerId(chatRoom.getCustomerId());
        dto.setCustomerName(chatRoom.getCustomerName());
        dto.setCustomerEmail(chatRoom.getCustomerEmail());
        dto.setAgentId(chatRoom.getAgentId());
        dto.setAgentName(chatRoom.getAgentName());
        dto.setStatus(chatRoom.getStatus());
        dto.setCreatedAt(chatRoom.getCreatedAt());
        dto.setClosedAt(chatRoom.getClosedAt());

        // Obtener último mensaje
        ChatMessage lastMessage = chatMessageRepository.findLastMessageByChatRoom(chatRoom.getId());
        if (lastMessage != null) {
            dto.setLastMessage(convertToDTO(lastMessage));
        }

        return dto;
    }

    private ChatMessageDTO convertToDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setChatRoomId(message.getChatRoom().getId());
        dto.setSenderId(message.getSenderId());
        dto.setSenderName(message.getSenderName());
        dto.setSenderType(message.getSenderType());
        dto.setContent(message.getContent());
        dto.setMessageType(message.getMessageType());
        dto.setTimestamp(message.getTimestamp());
        dto.setIsRead(message.getIsRead());
        return dto;
    }
}