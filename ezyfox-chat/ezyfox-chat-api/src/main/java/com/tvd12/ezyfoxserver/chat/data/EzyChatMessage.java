package com.tvd12.ezyfoxserver.chat.data;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity(value = "message")
@EzyArrayBinding(indexes = {"message", "receiver", "sender"})
public class EzyChatMessage implements Serializable {
	private static final long serialVersionUID = 6130168551127865806L;

	@Id
	private ObjectId id;
	
	private String sender;
	private String receiver;
	private String message;
	
	
}
