package com.tvd12.ezyfoxserver.chat.data;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class EzyChatError {

	private final int code;
	private final String message;
	
}
