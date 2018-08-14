package com.tvd12.chat.auth.consumer.entity;

import java.io.Serializable;
import java.util.Date;

import com.tvd12.ezyfox.database.entity.EzyEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChatAccessService implements EzyEntity {
	private static final long serialVersionUID = -4404089664422487805L;
	
	@org.springframework.data.annotation.Id
	private Id id;
	private long limit;
	private long expiredTime;
	private Date creationDate;
	private Date lastRegisterDate;
	private boolean autoRenewal;
	
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Id implements Serializable {
		private static final long serialVersionUID = 2079081725970112520L;
		
		protected String accessToken;
		protected String serviceName;
	}
	
}
