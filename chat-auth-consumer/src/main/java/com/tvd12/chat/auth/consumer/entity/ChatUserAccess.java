package com.tvd12.chat.auth.consumer.entity;

import java.io.Serializable;
import java.util.Date;

import com.tvd12.ezyfoxserver.database.entity.EzyEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChatUserAccess implements EzyEntity {
	private static final long serialVersionUID = -5449689482959331172L;
	
	@org.springframework.data.annotation.Id
	private Id id;
	private String accessToken;
	private long expiredTime;
	private Date creationDate;
	private Date lastRegisterDate;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Id implements Serializable {
		private static final long serialVersionUID = 6872022626808826664L;
		
		private long consumerId;
		private String userId;
	}
	
}
