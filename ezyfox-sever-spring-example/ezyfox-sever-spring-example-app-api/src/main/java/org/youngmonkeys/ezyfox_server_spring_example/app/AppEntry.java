package org.youngmonkeys.ezyfox_server_spring_example.app;

import org.youngmonkeys.ezyfox_server_spring_example.common.constant.CommonConstants;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultAppEntry;

import java.util.Properties;

public class AppEntry extends EzyDefaultAppEntry {

    @Override
    protected void preConfig(EzyAppContext ctx) {
        logger.info("\n=================== ezyfox-sever-spring-example APP START CONFIG ================\n");
    }

    @Override
    protected void postConfig(EzyAppContext ctx) {
        logger.info("\n=================== ezyfox-sever-spring-example APP END CONFIG ================\n");
    }

    @Override
    protected void setupBeanContext(EzyAppContext context, EzyBeanContextBuilder builder) {
        EzyZoneContext zoneContext = context.getParent();
        Properties pluginProperties = zoneContext.getProperty(CommonConstants.PLUGIN_PROPERTIES);
        EzyAppSetting setting = context.getApp().getSetting();
        builder.addProperties("ezyfox-sever-spring-example-common-config.properties");
        builder.addProperties(pluginProperties);
        builder.addProperties(getConfigFile(setting));
    }

    protected String getConfigFile(EzyAppSetting setting) {
        return setting.getConfigFile();
    }

    @Override
    protected String[] getScanablePackages() {
        return new String[]{
            "org.youngmonkeys.ezyfox_server_spring_example.common",
            "org.youngmonkeys.ezyfox_server_spring_example.app"
        };
    }
}
