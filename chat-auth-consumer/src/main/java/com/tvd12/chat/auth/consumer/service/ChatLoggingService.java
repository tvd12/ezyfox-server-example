package com.tvd12.chat.auth.consumer.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ChatLoggingService {

	/**
	 * Log when service file
	 * 
	 * @param fileName the file name
	 * @param request the request
	 */
	void whenServeFile(String fileName, HttpServletRequest request);

	/**
	 * Log when upload file
	 * 
	 * @param file the file
	 * @param request the request
	 */
	void whenUploadFile(MultipartFile file, HttpServletRequest request);

}
