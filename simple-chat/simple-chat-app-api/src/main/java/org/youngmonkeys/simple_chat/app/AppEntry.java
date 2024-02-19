package org.youngmonkeys.simple_chat.app;

import com.tvd12.ezydata.database.EzyDatabaseContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.impl.EzyBeanNameParser;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyZoneContext;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultAppEntry;
import org.youngmonkeys.simple_chat.common.constant.CommonConstants;

import java.util.Properties;

public class AppEntry extends EzyDefaultAppEntry {

    @Override
    protected void preConfig(EzyAppContext ctx) {
        logger.info("\n=================== simple-chat APP START CONFIG ================\n");
    }

    @Override
    protected void postConfig(EzyAppContext ctx) {
        logger.info("\n=================== simple-chat APP END CONFIG ================\n");
    }

    @Override
    protected void setupBeanContext(EzyAppContext context, EzyBeanContextBuilder builder) {
        EzyZoneContext zoneContext = context.getParent();
        EzyDatabaseContext databaseContext = zoneContext.getProperty(
            EzyDatabaseContext.class
        );
        databaseContext.getRepositories().forEach((repoType, repo) -> {
            builder.addSingleton(EzyBeanNameParser.getBeanName(repoType), repo);
        });
        Properties pluginProperties = zoneContext.getProperty(CommonConstants.PLUGIN_PROPERTIES);
        EzyAppSetting setting = context.getApp().getSetting();
        builder.addProperties("simple-chat-common-config.properties");
        builder.addProperties(pluginProperties);
        builder.addProperties(getConfigFile(setting));
    }

    protected String getConfigFile(EzyAppSetting setting) {
        return setting.getConfigFile();
    }

    @Override
    protected String[] getScanablePackages() {
        return new String[]{
            "org.youngmonkeys.simple_chat.common",
            "org.youngmonkeys.simple_chat.app"
        };
    }
}
