package com.example.hello_world.exception;

public class InvalidChatRequestException extends RuntimeException {
    private static final long serialVersionUID = -1179650684958813337L;

    public InvalidChatRequestException(String msg) {
        super(msg);
    }
}
