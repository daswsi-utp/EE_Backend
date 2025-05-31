
// ChatRoomRepository.java
package com.chat_service.repository;

import com.chat_service.entity.ChatRoom;
import com.chat_service.entity.ChatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // Buscar chat activo por cliente
    Optional<ChatRoom> findByCustomerIdAndStatus(String customerId, ChatStatus status);

    // Buscar chats por agente
    List<ChatRoom> findByAgentIdAndStatus(String agentId, ChatStatus status);

    // Buscar chats esperando agente
    List<ChatRoom> findByStatusOrderByCreatedAtAsc(ChatStatus status);

    // Buscar historial de chats del cliente
    List<ChatRoom> findByCustomerIdOrderByCreatedAtDesc(String customerId);

    // Contar chats en espera
    @Query("SELECT COUNT(c) FROM ChatRoom c WHERE c.status = :status")
    Long countByStatus(@Param("status") ChatStatus status);

    // Buscar chats activos de un agente
    @Query("SELECT c FROM ChatRoom c WHERE c.agentId = :agentId AND c.status = 'ACTIVE'")
    List<ChatRoom> findActiveChatsByAgent(@Param("agentId") String agentId);
}