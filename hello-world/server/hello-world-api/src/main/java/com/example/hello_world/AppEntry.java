package com.example.hello_world;

import com.example.hello_world.config.AppConfig;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimpleAppEntry;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

public class AppEntry extends EzySimpleAppEntry {

	@Override
	protected void preConfig(EzyAppContext ctx) {
		logger.info("\n=================== HELLO-WORD APP START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyAppContext ctx) {
		logger.info("\n=================== HELLO-WORD APP END CONFIG ================\n");
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
		getLogger().info("start hello-world app");
	}

	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
				"com.example.hello_world.common",
				"com.example.hello_world.controller",
				"com.example.hello_world.handler"
		};
	}
	
	@Override
	protected String[] getScanableBindingPackages() {
		return new String[] {
			"com.example.hello_world.handler"
		};
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
