package com.tvd12.chat.auth.consumer.data;

import com.tvd12.ezyfox.constant.EzyError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatError implements EzyError {

	private int code;
	private String message;

	public static final ChatError INVALID_CONSUMER_KEY 
		= new ChatError(1, "INVALID_CONSUMER_KEY");
	public static final ChatError INVALID_ACCESS_TOKEN 
		= new ChatError(2, "INVALID_ACCESS_TOKEN");
	public static final ChatError INVALID_USER_ACCESS_TOKEN
		= new ChatError(3, "INVALID_USER_ACCESS_TOKEN");

}
