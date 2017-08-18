package com.tvd12.chat.auth.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tvd12.chat.auth.consumer.entity.ChatAccessService;
import com.tvd12.chat.auth.consumer.entity.ChatConsumer;
import com.tvd12.chat.auth.consumer.entity.ChatUserAccess;
import com.tvd12.chat.auth.consumer.repo.ChatAccessServiceRepository;
import com.tvd12.chat.auth.consumer.repo.ChatCustomerRepository;
import com.tvd12.chat.auth.consumer.repo.ChatUserAccessRepository;
import com.tvd12.chat.auth.consumer.service.ChatConsumerService;
import com.tvd12.chat.auth.consumer.service.ChatIdService;

@Service
public class ChatMongoConsumerService implements ChatConsumerService {
	
	@Autowired
	private ChatIdService idService;
	
	@Autowired
	private ChatCustomerRepository customerRepo;
	
	@Autowired
	private ChatUserAccessRepository userAccessRepo;
	
	@Autowired
	private ChatAccessServiceRepository accessServiceRepo;
	
	@Override
	public void registerConsumer(
			String superAccessToken, 
			ChatConsumer consumer) {
		customerRepo.save(consumer);
	}
	
	@Override
	public ChatConsumer getConsumer(
			String consumerKey) {
		return customerRepo.findOneByConsumerKey(consumerKey);
	}

	@Override
	public String getUserAccessToken(
			String consumerKey, 
			String accessToken, 
			String secretKey, 
			String userId) {
		ChatConsumer customer = getConsumer(consumerKey);
		ChatAccessService.Id accessServiceId = new ChatAccessService.Id(accessToken, "customer-user");
		ChatAccessService accessService = getAccessService(accessServiceId);
		ChatUserAccess.Id userAccesssId = new ChatUserAccess.Id(customer.getId(), userId);
		ChatUserAccess userAccess = getUserAccess(userAccesssId);
		return userAccess.getAccessToken();
	}
	
	protected ChatUserAccess getUserAccess(ChatUserAccess.Id id) {
		return userAccessRepo.findOne(id);
	}
	
	protected ChatAccessService getAccessService(ChatAccessService.Id id) {
		return accessServiceRepo.findOne(id);
	}

}
