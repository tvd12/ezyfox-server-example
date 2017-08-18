package com.tvd12.chat.auth.consumer.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.service.ChatIdService;

@Service
public class ChatSimpleIdService implements ChatIdService {

	@Override
	public long generate(String serviceName) {
		return ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	}
	
}
