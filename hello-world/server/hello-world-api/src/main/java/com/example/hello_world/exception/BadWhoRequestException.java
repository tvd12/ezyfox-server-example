package com.example.hello_world.exception;

public class BadWhoRequestException extends RuntimeException {
    private static final long serialVersionUID = -1179650684958813337L;
    
    public BadWhoRequestException(String msg) {
        super(msg);
    }
}
