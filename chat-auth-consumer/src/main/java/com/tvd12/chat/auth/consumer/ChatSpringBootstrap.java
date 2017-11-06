package com.tvd12.chat.auth.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.tvd12.ezyfoxserver.util.EzyDestroyable;
import com.tvd12.ezyfoxserver.util.EzyLoggable;
import com.tvd12.ezyfoxserver.util.EzyStartable;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class ChatSpringBootstrap 
		extends EzyLoggable implements EzyStartable, EzyDestroyable {

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
