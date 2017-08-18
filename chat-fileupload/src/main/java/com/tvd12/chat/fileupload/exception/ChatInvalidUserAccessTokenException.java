package com.tvd12.chat.fileupload.exception;

import org.springframework.http.HttpStatus;

import com.tvd12.chat.fileupload.data.ChatFileUploadError;

public class ChatInvalidUserAccessTokenException extends ChatBadRequestException {
	private static final long serialVersionUID = 2631676219672134918L;
	
	public ChatInvalidUserAccessTokenException() {
		super(HttpStatus.UNAUTHORIZED, ChatFileUploadError.INVALID_USER_ACCESS_TOKEN);
	}

}
