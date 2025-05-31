// Custom Exceptions
package com.chat_service.exception;

public class ChatRoomAlreadyExistsException extends RuntimeException {
    public ChatRoomAlreadyExistsException(String message) {
        super(message);
    }
}

