package com.tvd12.chat.auth.consumer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tvd12.chat.auth.consumer.entity.ChatConsumer;

public interface ChatCustomerRepository extends MongoRepository<ChatConsumer, Long> {

	ChatConsumer findOneByConsumerKey(String consumerKey);
	
}
