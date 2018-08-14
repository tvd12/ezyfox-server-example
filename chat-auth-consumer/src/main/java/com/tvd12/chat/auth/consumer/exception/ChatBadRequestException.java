package com.tvd12.chat.auth.consumer.exception;

import org.springframework.http.HttpStatus;

import com.tvd12.ezyfox.constant.EzyError;

import lombok.Getter;

@Getter
public class ChatBadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1035900732064692833L;

	protected int statusCode;
	protected EzyError error;
	
	public ChatBadRequestException(int statusCode, EzyError error) {
		this.error = error;
		this.statusCode = statusCode;
	}
	
	public ChatBadRequestException(HttpStatus status, EzyError error) {
		this(status.value(), error);
	}
	
	@Override
	public String getMessage() {
		return error.getMessage();
	}
	
}
