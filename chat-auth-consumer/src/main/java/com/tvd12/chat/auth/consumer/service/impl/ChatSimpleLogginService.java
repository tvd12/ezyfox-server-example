package com.tvd12.chat.auth.consumer.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.tvd12.chat.auth.consumer.service.ChatLoggingService;
import com.tvd12.ezyfox.util.EzyLoggable;

public class ChatSimpleLogginService 
		extends EzyLoggable 
		implements ChatLoggingService {

	@Override
	public void whenServeFile(String fileName, HttpServletRequest request) {
		getLogger().info("client at ip {} request file {}", request.getRemoteAddr(), fileName);
	}
	
	@Override
	public void whenUploadFile(MultipartFile file, HttpServletRequest request) {
		getLogger().info("client at ip {} upload file {}", request.getRemoteAddr(), file.getOriginalFilename());
	}
	
}
