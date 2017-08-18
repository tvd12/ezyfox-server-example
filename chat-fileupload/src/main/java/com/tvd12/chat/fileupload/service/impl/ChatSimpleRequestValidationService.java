package com.tvd12.chat.fileupload.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tvd12.chat.fileupload.service.ChatRequestValidationService;

public class ChatSimpleRequestValidationService implements ChatRequestValidationService {

	@Override
	public boolean validate(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		return true;
	}
	
}
