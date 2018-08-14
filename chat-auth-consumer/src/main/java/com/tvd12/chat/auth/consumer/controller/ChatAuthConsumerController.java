package com.tvd12.chat.auth.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.tvd12.chat.auth.consumer.data.ChatMessage;
import com.tvd12.chat.auth.consumer.entity.ChatConsumer;
import com.tvd12.chat.auth.consumer.service.ChatConsumerAccessTokenService;
import com.tvd12.chat.auth.consumer.service.ChatConsumerKeyService;
import com.tvd12.chat.auth.consumer.service.ChatConsumerSecretKeyService;
import com.tvd12.chat.auth.consumer.service.ChatConsumerService;
import com.tvd12.chat.auth.consumer.service.ChatIdService;
import com.tvd12.ezyfox.util.EzyLoggable;

@RestController
@RequestMapping("chat/consumer")
public class ChatAuthConsumerController extends EzyLoggable {
	
	@Autowired
	private ChatIdService idService;
	
	@Autowired
	private ChatConsumerService consumerService;
	
	@Autowired
	private ChatConsumerKeyService consumerKeyService;
	
	@Autowired
	private ChatConsumerSecretKeyService secretKeyService;
	
	@Autowired
	private ChatConsumerAccessTokenService accessTokenService;
	
	@PostMapping("register")
	public ResponseEntity<ChatConsumer> registerConsumer(
			@RequestHeader("SuperAccessToken") String superAccesstoken,
			@RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber) {
		long id = idService.generate("consumer");
		ChatConsumer consumer = new ChatConsumer();
		consumer.setId(id); 
		consumer.setEmail(email);
		consumer.setPhoneNumber(phoneNumber);
		consumer.setConsumerKey(consumerKeyService.generate(id));
		consumer.setAccessToken(accessTokenService.generate(id));
		consumer.setSecretToken(secretKeyService.generate(id));
		consumerService.registerConsumer(superAccesstoken, consumer);
		return ResponseEntity.ok(consumer);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ChatMessage> handleDuplicateException(DuplicateKeyException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ChatMessage("DUPLICATE_CONSUMER"));
	}

}
