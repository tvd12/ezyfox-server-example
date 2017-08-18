package com.tvd12.chat.auth.consumer.service;

import com.tvd12.chat.auth.consumer.entity.ChatConsumer;

public interface ChatConsumerService {
	
	void registerConsumer(
			String superAccessToken, 
			ChatConsumer consumer);
	
	ChatConsumer getConsumer(
			String consumerKey);
	
	String getUserAccessToken(
			String consumerKey, 
			String accessToken,
			String secretKey,
			String userId);
	
}
