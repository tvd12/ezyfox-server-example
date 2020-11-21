/**
 * 
 */
package com.example.hello_world.plugin;

import com.example.hello_world.plugin.config.PluginConfig;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimplePluginEntry;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

/**
 * @author tavandung12
 *
 */
public class PluginEntry extends EzySimplePluginEntry {

	@Override
	protected void preConfig(EzyPluginContext ctx) {
		logger.info("\n=================== HELLO-WORLD PLUGIN START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyPluginContext ctx) {
		logger.info("\n=================== HELLO-WORLD PLUGIN END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
		EzyPluginSetting setting = context.getPlugin().getSetting();
		String pluginConfigFile = getConfigFile(setting);
		PluginConfig pluginConfig = readPluginConfig(pluginConfigFile);
		logger.info("hello-world plugin config: {}", pluginConfig);
	}
	
	protected String getConfigFile(EzyPluginSetting setting) {
		return setting.getConfigFile();
	}

	@Override
	public void start() throws Exception {
		getLogger().info("hello-world plugin: start");
	}
	
	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
			"com.example.hello_world.plugin",
		};
	}

    private PluginConfig readPluginConfig(String appConfigFile) {
		return new PropertiesMapper()
				.file(appConfigFile)
				.context(getClass())
				.clazz(PluginConfig.class)
				.reader(new BaseFileReader())
				.map();
	}

}