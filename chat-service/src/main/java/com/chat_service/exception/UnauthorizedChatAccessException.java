// Custom Exceptions
package com.chat_service.exception;

public class UnauthorizedChatAccessException extends RuntimeException {
    public UnauthorizedChatAccessException(String message) {
        super(message);
    }
}