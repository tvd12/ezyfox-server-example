package com.tvd12.chat.fileupload;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.tvd12.chat.fileupload.service.impl.ChatFileSystemUploadService;
import com.tvd12.chat.fileupload.service.impl.ChatSimpleLogginService;
import com.tvd12.chat.fileupload.service.impl.ChatSimpleRequestValidationService;

import lombok.Setter;

@Setter
public class ChatSpringApplication extends SpringApplication {

	public ChatSpringApplication(Object... sources) {
		super(sources);
	}
	
	@Override
	protected ConfigurableApplicationContext createApplicationContext() {
		ConfigurableApplicationContext ctx = super.createApplicationContext();
		ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
		beanFactory.registerSingleton("loggingService", new ChatSimpleLogginService());
		beanFactory.registerSingleton("fileUploadService", new ChatFileSystemUploadService());
		beanFactory.registerSingleton("requestValidationService", new ChatSimpleRequestValidationService());
		return ctx;
	}
	
	@Override
	protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
		super.configureEnvironment(environment, args);
	}
	
}
