package com.example.hello_world.config;

import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import com.tvd12.properties.file.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyPropertiesBean
public class AppConfig {

	@Property("node.name")
	private String nodeName;
	
}
