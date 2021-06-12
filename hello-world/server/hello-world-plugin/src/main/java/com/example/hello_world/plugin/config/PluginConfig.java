package com.example.hello_world.plugin.config;

import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;

import lombok.Data;

@Data
@EzyPropertiesBean
public class PluginConfig {

	private String environment;
	private DatabaseConfig database;
	
	@Data
	@EzyPropertiesBean(prefix = "database")
	public static class DatabaseConfig {
		private String name;
		private String host;
		private int port;
	}
	
}
