package com.tvd12.chat.fileupload.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.tvd12.chat.fileupload.constant.ChatHeaderKey;
import com.tvd12.chat.fileupload.exception.ChatInvalidAccessTokenException;
import com.tvd12.chat.fileupload.exception.ChatInvalidConsumerKeyException;
import com.tvd12.chat.fileupload.exception.ChatInvalidUserAccessTokenException;
import com.tvd12.chat.fileupload.service.ChatRequestValidationService;

public class ChatHttpRequestValidationService implements ChatRequestValidationService {

	@Override
	public boolean validate(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String comsumerKey = request.getHeader(ChatHeaderKey.CONSUMER_KEY);
		String accessToken = request.getHeader(ChatHeaderKey.ACCESS_TOKEN);
		String userAccessToken = request.getHeader(ChatHeaderKey.USER_ACCESS_TOKEN);
		if(StringUtils.isEmpty(comsumerKey))
			throw new ChatInvalidConsumerKeyException();
		if(StringUtils.isEmpty(accessToken))
			throw new ChatInvalidAccessTokenException();
		if(StringUtils.isEmpty(userAccessToken))
			throw new ChatInvalidUserAccessTokenException();
		return true;
	}
}
