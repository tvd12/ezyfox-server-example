package com.tvd12.chat.auth.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.service.ChatConsumerKeyService;
import com.tvd12.ezyfoxserver.sercurity.EzyBase64;

@Service
public class ChatSimpleConsumerKeyService implements ChatConsumerKeyService {

	@Override
	public String generate(long consumerId) {
		String answer = "ConsumerKey#" + consumerId;
		return EzyBase64.encodeUtf(answer);
	}

}
