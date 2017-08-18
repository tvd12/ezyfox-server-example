package com.tvd12.chat.auth.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.Setter;

@Setter
public class ChatSpringApplication extends SpringApplication {

	public ChatSpringApplication(Object... sources) {
		super(sources);
	}
	
	@Override
	protected ConfigurableApplicationContext createApplicationContext() {
		return super.createApplicationContext();
	}
	
	@Override
	protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
		super.configureEnvironment(environment, args);
	}
	
}
