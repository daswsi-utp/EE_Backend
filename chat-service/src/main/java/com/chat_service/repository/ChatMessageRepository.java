package com.chat_service.repository;

import com.chat_service.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Buscar mensajes por chat room
    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);

    // Contar mensajes no leídos
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.chatRoom.id = :chatRoomId AND m.isRead = false AND m.senderId != :userId")
    Long countUnreadMessages(@Param("chatRoomId") Long chatRoomId, @Param("userId") String userId);

    // Marcar mensajes como leídos
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.chatRoom.id = :chatRoomId AND m.senderId != :userId")
    void markMessagesAsRead(@Param("chatRoomId") Long chatRoomId, @Param("userId") String userId);

    // Obtener último mensaje del chat
    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.timestamp DESC LIMIT 1")
    ChatMessage findLastMessageByChatRoom(@Param("chatRoomId") Long chatRoomId);
}