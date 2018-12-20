package com.example.hello_world.plugin.config;

import com.tvd12.properties.file.annotation.Property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PluginConfig {

	@Property("node.name")
	private String nodeName;
	
}
