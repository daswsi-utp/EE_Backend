// AssignAgentRequest.java
package com.chat_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignAgentRequest {
    @NotNull(message = "Chat room ID is required")
    private Long chatRoomId;

    @NotBlank(message = "Agent ID is required")
    private String agentId;

    @NotBlank(message = "Agent name is required")
    private String agentName;
}