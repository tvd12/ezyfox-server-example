package com.tvd12.chat.fileupload.data;

import com.tvd12.ezyfoxserver.constant.EzyError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatFileUploadError implements EzyError {

	private int code;
	private String message;

	public static final ChatFileUploadError INVALID_CONSUMER_KEY 
		= new ChatFileUploadError(1, "INVALID_CONSUMER_KEY");
	public static final ChatFileUploadError INVALID_ACCESS_TOKEN 
		= new ChatFileUploadError(2, "INVALID_ACCESS_TOKEN");
	public static final ChatFileUploadError INVALID_USER_ACCESS_TOKEN
		= new ChatFileUploadError(3, "INVALID_USER_ACCESS_TOKEN");

}
