// WebSocketController.java
package com.chat_service.controller;

import com.chat_service.dto.SendMessageRequest;
import com.chat_service.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat/{chatRoomId}")
    public void sendMessage(@Payload SendMessageRequest message) {
        chatService.sendMessage(message);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chat/{chatRoomId}")
    public String addUser(@Payload String userName, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", userName);
        return userName + " joined the chat";
    }
}