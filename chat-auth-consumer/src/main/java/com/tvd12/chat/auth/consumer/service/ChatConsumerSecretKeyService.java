package com.tvd12.chat.auth.consumer.service;

public interface ChatConsumerSecretKeyService {

	String generate(long consumerId);
	
}
