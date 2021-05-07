package com.example.simple_chat;

import com.example.simple_chat.config.AppConfig;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.app.EzyAppRequestController;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.controller.EzyUserRequestAppSingletonController;
import com.tvd12.ezyfoxserver.support.entry.EzySimpleAppEntry;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

public class AppEntry extends EzySimpleAppEntry {

	@Override
	protected void preConfig(EzyAppContext ctx) {
		logger.info("\n=================== SIMPLE-CHAT APP START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyAppContext ctx) {
		logger.info("\n=================== SIMPLE-CHAT APP END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyAppContext context, EzyBeanContextBuilder builder) {
		EzyAppSetting setting = context.getApp().getSetting();
		String appConfigFile = getConfigFile(setting);
		AppConfig appConfig = readAppConfig(appConfigFile);
		logger.info("hello-word app config: {}", appConfig);
	}
	
	protected String getConfigFile(EzyAppSetting setting) {
		return setting.getConfigFile();
	}
	
	@Override
	public void start() throws Exception {
		getLogger().info("start simple-chat app");
	}

	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
				"com.example.simple_chat.common",
				"com.example.simple_chat.controller",
				"com.example.simple_chat.handler"
		};
	}
	
	@Override
	protected String[] getScanableBindingPackages() {
		return new String[] {
			"com.example.simple_chat.handler"
		};
	}
	
	@Override
	protected EzyAppRequestController newUserRequestController(EzyBeanContext beanContext) {
		return EzyUserRequestAppSingletonController.builder()
				.beanContext(beanContext)
				.build();
	}

	private AppConfig readAppConfig(String appConfigFile) {
		return new PropertiesMapper()
				.file(appConfigFile)
				.context(getClass())
				.clazz(AppConfig.class)
				.reader(new BaseFileReader())
				.map();
	}

}
