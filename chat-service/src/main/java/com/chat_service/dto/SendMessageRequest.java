// SendMessageRequest.java
package com.chat_service.dto;

import com.chat_service.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    @NotNull(message = "Chat room ID is required")
    private Long chatRoomId;

    @NotBlank(message = "Sender ID is required")
    private String senderId;

    @NotBlank(message = "Sender name is required")
    private String senderName;

    @NotBlank(message = "Content is required")
    private String content;

    private MessageType messageType = MessageType.TEXT;
}