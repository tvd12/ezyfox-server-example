package com.tvd12.example.lucky_wheel.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Data;

import java.util.Date;

@Data
@EzyCollection
public class Prize {
	@EzyId
	Long id;
	
	private Date date = new Date();
	private String username;
	private int prize;
}
