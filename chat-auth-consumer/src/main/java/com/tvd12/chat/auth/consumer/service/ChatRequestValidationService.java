package com.tvd12.chat.auth.consumer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ChatRequestValidationService {

	boolean validate(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
	
}
