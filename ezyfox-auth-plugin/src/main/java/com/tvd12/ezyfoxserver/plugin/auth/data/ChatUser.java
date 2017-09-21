package com.tvd12.ezyfoxserver.plugin.auth.data;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@ToString
@Entity(value="userchats")
@EzyArrayBinding(indexes={"userName","passWord","onlineFlag"})
public class ChatUser implements Serializable{
	private static final long serialVersionUID = 6130168551127890806L;
	@Id
	private ObjectId id;
	private String userName;
	private String passWord;
	private String onlineFlag;
}
