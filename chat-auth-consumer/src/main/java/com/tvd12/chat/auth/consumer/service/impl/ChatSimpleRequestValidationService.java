package com.tvd12.chat.auth.consumer.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.service.ChatRequestValidationService;

@Service
public class ChatSimpleRequestValidationService implements ChatRequestValidationService {

	@Override
	public boolean validate(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		return true;
	}
	
}
