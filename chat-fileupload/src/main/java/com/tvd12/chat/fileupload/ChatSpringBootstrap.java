package com.tvd12.chat.fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.tvd12.ezyfoxserver.entity.EzyDestroyable;
import com.tvd12.ezyfoxserver.entity.EzyStartable;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class ChatSpringBootstrap 
		extends SpringBootServletInitializer 
		implements EzyStartable, EzyDestroyable {

	protected ConfigurableApplicationContext applicationContext;
	
	@Override
	public void start() throws Exception {
		SpringApplication app = newApplication();
		applicationContext = app.run();
	}
	
	@Override
	public void destroy() {
		SpringApplication.exit(applicationContext, () -> 0);
	}
	
	protected SpringApplication newApplication() {
		ChatSpringApplication app = new ChatSpringApplication(getClass());
		return app;
	}
	
}
