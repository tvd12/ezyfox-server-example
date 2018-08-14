package com.tvd12.ezyfoxserver.ex.videopoker.config;

import com.tvd12.properties.file.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppConfig {

	@Property("database.name")
	private String databaseName;
	
}
