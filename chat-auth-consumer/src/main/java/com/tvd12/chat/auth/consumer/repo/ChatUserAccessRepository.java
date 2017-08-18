package com.tvd12.chat.auth.consumer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tvd12.chat.auth.consumer.entity.ChatUserAccess;

public interface ChatUserAccessRepository 
		extends MongoRepository<ChatUserAccess, ChatUserAccess.Id> {
	
}
