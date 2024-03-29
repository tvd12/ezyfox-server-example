package com.tvd12.example.lucky_wheel.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

@Data
@EzyCollection
public class User {
	@EzyId
	Long id;
	
	String username;
	String password;
}
