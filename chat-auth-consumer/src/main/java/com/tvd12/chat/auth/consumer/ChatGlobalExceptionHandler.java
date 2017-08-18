package com.tvd12.chat.auth.consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tvd12.chat.auth.consumer.exception.ChatBadRequestException;

@ControllerAdvice
public class ChatGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ChatBadRequestException.class)
	public ResponseEntity<Object> handleAuthenticationException(
			ChatBadRequestException e, 
			WebRequest request) {
		return newResponseEntity(e);
	}
	
	protected ResponseEntity<Object> newResponseEntity(ChatBadRequestException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.body(e.getError());
	}
	
}
