// ChatMessageDTO.java
package com.chat_service.dto;

import com.chat_service.entity.MessageType;
import com.chat_service.entity.SenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private Long chatRoomId;
    private String senderId;
    private String senderName;
    private SenderType senderType;
    private String content;
    private MessageType messageType;
    private LocalDateTime timestamp;
    private Boolean isRead;
}
