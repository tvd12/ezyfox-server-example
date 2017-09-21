package com.tvd12.ezyfoxserver.chat.client.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUser {
	String userName;
	String passWord;
	boolean onlineFlag;
	@Override
	public String toString() {
		return new StringBuilder()
				.append(userName)
				.toString();
	}
}
