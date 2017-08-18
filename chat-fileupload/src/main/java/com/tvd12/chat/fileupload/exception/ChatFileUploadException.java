package com.tvd12.chat.fileupload.exception;

public class ChatFileUploadException extends RuntimeException {
	private static final long serialVersionUID = 1313805612668514146L;

	public ChatFileUploadException(String message) {
        super(message);
    }

    public ChatFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
