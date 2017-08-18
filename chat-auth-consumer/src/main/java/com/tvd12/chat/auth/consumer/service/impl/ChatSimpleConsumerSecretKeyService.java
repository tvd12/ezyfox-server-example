package com.tvd12.chat.auth.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.service.ChatConsumerSecretKeyService;
import com.tvd12.ezyfoxserver.sercurity.EzyBase64;

@Service
public class ChatSimpleConsumerSecretKeyService implements ChatConsumerSecretKeyService {

	@Override
	public String generate(long consumerId) {
		String answer = "ConsumerSecretKey#" + consumerId;
		return EzyBase64.encodeUtf(answer);
	}

}
