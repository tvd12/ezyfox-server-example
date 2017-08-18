package com.tvd12.chat.auth.consumer.exception;

import org.springframework.http.HttpStatus;

import com.tvd12.chat.auth.consumer.data.ChatError;

public class ChatInvalidConsumerKeyException extends ChatBadRequestException {
	private static final long serialVersionUID = 2631676219672134918L;
	
	public ChatInvalidConsumerKeyException() {
		super(HttpStatus.UNAUTHORIZED, ChatError.INVALID_CONSUMER_KEY);
	}

}
