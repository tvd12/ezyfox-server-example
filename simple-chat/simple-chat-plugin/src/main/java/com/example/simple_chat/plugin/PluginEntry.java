/**
 * 
 */
package com.example.simple_chat.plugin;

import com.example.simple_chat.plugin.config.PluginConfig;
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
		logger.info("\n=================== SIMPLE-CHAT PLUGIN START CONFIG ================\n");
	}
	
	@Override
	protected void postConfig(EzyPluginContext ctx) {
		logger.info("\n=================== SIMPLE-CHAT PLUGIN END CONFIG ================\n");
	}
	
	@Override
	protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
		EzyPluginSetting setting = context.getPlugin().getSetting();
		String pluginConfigFile = getConfigFile(setting);
		PluginConfig pluginConfig = readPluginConfig(pluginConfigFile);
		logger.info("simple-chat plugin config: {}", pluginConfig);
	}
	
	protected String getConfigFile(EzyPluginSetting setting) {
		return setting.getConfigFile();
	}

	@Override
	public void start() throws Exception {
		getLogger().info("simple-chat plugin: start");
	}
	
	@Override
	protected String[] getScanableBeanPackages() {
		return new String[] {
			"com.example.simple_chat.plugin",
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