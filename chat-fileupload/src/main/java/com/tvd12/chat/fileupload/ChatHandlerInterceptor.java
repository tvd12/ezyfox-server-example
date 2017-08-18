package com.tvd12.chat.fileupload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tvd12.chat.fileupload.service.ChatRequestValidationService;

@Component("handlerInterceptor")
public class ChatHandlerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ChatRequestValidationService requestValidationService;
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		return requestValidationService.validate(request, response);
	}
	
}
