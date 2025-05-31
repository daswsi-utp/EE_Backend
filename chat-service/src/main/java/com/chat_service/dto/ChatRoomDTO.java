// ChatRoomDTO.java
package com.chat_service.dto;

import com.chat_service.entity.ChatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String agentId;
    private String agentName;
    private ChatStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private List<ChatMessageDTO> messages;
    private Long unreadCount;
    private ChatMessageDTO lastMessage;
}