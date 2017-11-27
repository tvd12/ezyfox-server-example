package com.tvd12.chat.auth.consumer.entity;

import org.springframework.data.annotation.Id;

import com.tvd12.ezyfoxserver.database.entity.EzyLongIdEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatConsumer implements EzyLongIdEntity {
	private static final long serialVersionUID = -713168031006166761L;
	
	@Id
	private Long id;
	private String email;
	private String phoneNumber;
	private String consumerKey;
	private String accessToken;
	private String secretToken;
}
