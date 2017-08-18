package com.tvd12.chat.fileupload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ChatRequestValidationService {

	boolean validate(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
	
}
