package org.youngmonkeys.ezyfox_server_spring_example.plugin;

import org.youngmonkeys.ezyfox_server_spring_example.common.constant.CommonConstants;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultPluginEntry;

import java.util.Properties;

public class PluginEntry extends EzyDefaultPluginEntry {

    @Override
    protected void preConfig(EzyPluginContext ctx) {
        logger.info("\n=================== ezyfox-sever-spring-example PLUGIN START CONFIG ================\n");
    }

    @Override
    protected void postConfig(EzyPluginContext ctx) {
        logger.info("\n=================== ezyfox-sever-spring-example PLUGIN END CONFIG ================\n");
    }

    @Override
    protected void setupBeanContext(EzyPluginContext context, EzyBeanContextBuilder builder) {
        EzyPluginSetting setting = context.getPlugin().getSetting();
        builder.addProperties("ezyfox-sever-spring-example-common-config.properties");
        builder.addProperties(getConfigFile(setting));

        Properties properties = builder.getProperties();
        EzyZoneContext zoneContext = context.getParent();
        zoneContext.setProperty(CommonConstants.PLUGIN_PROPERTIES, properties);
    }

    protected String getConfigFile(EzyPluginSetting setting) {
        return setting.getConfigFile();
    }

    @Override
    protected String[] getScanablePackages() {
        return new String[]{
            "org.youngmonkeys.ezyfox_server_spring_example.common",
            "org.youngmonkeys.ezyfox_server_spring_example.plugin"
        };
    }
}
