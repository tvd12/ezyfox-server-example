/**
 *
 */
package com.example.hello_world.plugin;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzySimplePluginEntry;

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
        builder.addProperties(pluginConfigFile);
        logger.info("hello-world plugin config file: {}", pluginConfigFile);
    }

    protected String getConfigFile(EzyPluginSetting setting) {
        return setting.getConfigFile();
    }

    @Override
    public void start() throws Exception {
        getLogger().info("hello-world plugin: start");
    }

    @Override
    protected String[] getScanablePackages() {
        return new String[]{
            "com.example.hello_world",
        };
    }
}