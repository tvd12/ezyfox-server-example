package com.tvd12.chat.auth.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.service.ChatConsumerAccessTokenService;
import com.tvd12.ezyfoxserver.sercurity.EzyBase64;

@Service
public class ChatSimpleConsumerAccessTokenService implements ChatConsumerAccessTokenService {

	@Override
	public String generate(long consumerId) {
		String answer = "ConsumerAccessToken#" + consumerId;
		return EzyBase64.encodeUtf(answer);
	}

}
