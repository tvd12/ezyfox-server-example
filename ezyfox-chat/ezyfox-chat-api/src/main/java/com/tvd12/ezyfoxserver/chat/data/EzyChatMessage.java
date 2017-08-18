package com.tvd12.ezyfoxserver.chat.data;

import java.io.Serializable;

import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyArrayBinding(indexes = {"message", "receiver", "sender"})
public class EzyChatMessage implements Serializable {
	private static final long serialVersionUID = 6130168551127865806L;

	private String sender;
	private String receiver;
	private String message;
	
	
}
