package org.youngmonkeys.example.push_message;

import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultAppEntry;
import com.tvd12.ezyfoxserver.support.entry.EzyDefaultPluginEntry;

public class PushMessageServerStartup {

    private static final String ZONE_NAME = "push-message";
    private static final String PLUGIN_NAME = "push-message";
    private static final String APP_NAME = "push-message";

    public static void main(String[] args) throws Exception {
        EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
            .name(PLUGIN_NAME)
            .addListenEvent(EzyEventType.USER_LOGIN)
            .entryLoader(PluginEntryLoader.class);

        EzyAppSettingBuilder appSettingBuilder = new EzyAppSettingBuilder()
            .name(APP_NAME)
            .entryLoader(AppEntryLoader.class);

        EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
            .name(ZONE_NAME)
            .application(appSettingBuilder.build())
            .plugin(pluginSettingBuilder.build());

        EzySimpleSettings settings = new EzySettingsBuilder()
            .zone(zoneSettingBuilder.build())
            .build();

        EzyEmbeddedServer server = EzyEmbeddedServer.builder()
            .settings(settings)
            .build();
        server.start();
    }

    public static class AppEntryLoader extends EzyAbstractAppEntryLoader {
        @Override
        public EzyAppEntry load() throws Exception {
            return new AppEntry();
        }
    }

    public static class AppEntry extends EzyDefaultAppEntry {
        @Override
        protected String[] getScanablePackages() {
            return new String[] {
                "org.youngmonkeys.example.push_message"
            };
        }
    }

    public static class PluginEntryLoader extends EzyAbstractPluginEntryLoader {
        @Override
        public EzyPluginEntry load() throws Exception {
            return new PluginEntry();
        }
    }

    public static class PluginEntry extends EzyDefaultPluginEntry {
        @Override
        protected String[] getScanablePackages() {
            return new String[] {
                "org.youngmonkeys.example.push_message"
            };
        }
    }
}
