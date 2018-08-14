package com.tvd12.ezyfoxserver.ex.videopoker.common.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.ex.videopoker.common.constant.Entities;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
@Entity(value = Entities.CHAT_USER, noClassnameStored = true)
@EzyArrayBinding(indexes={"username"}, read = false)
public class User extends Data {
	private static final long serialVersionUID = 6130168551127890806L;
	
	@Id
	private String username;
	private String password;
	
}
