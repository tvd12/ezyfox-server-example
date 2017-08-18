package com.tvd12.chat.auth.consumer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tvd12.chat.auth.consumer.entity.ChatAccessService;

public interface ChatAccessServiceRepository 
		extends MongoRepository<ChatAccessService, ChatAccessService.Id> {
	
}
