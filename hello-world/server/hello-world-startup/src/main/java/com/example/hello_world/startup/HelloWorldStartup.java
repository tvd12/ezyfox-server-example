package com.example.hello_world.startup;

import com.example.hello_world.AppEntry;
import com.example.hello_world.AppEntryLoader;
import com.example.hello_world.plugin.PluginEntry;
import com.example.hello_world.plugin.PluginEntryLoader;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.constant.SslType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloWorldStartup {

    public static void main(String[] args) throws Exception {

        EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
            .name("hello-world")
            .addListenEvent(EzyEventType.USER_LOGIN)
            .entryLoader(DecoratedPluginEntryLoader.class);

        EzyAppSettingBuilder appSettingBuilder = new EzyAppSettingBuilder()
            .name("hello-world")
            .entryLoader(DecoratedAppEntryLoader.class);

        EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
            .name("example")
            .application(appSettingBuilder.build())
            .plugin(pluginSettingBuilder.build());

        EzySocketSettingBuilder socketSettingBuilder = new EzySocketSettingBuilder()
            .sslActive(true)
            .sslType(SslType.CERTIFICATION)
            .sslHandshakeTimeout(300)
            .maxRequestSize(512);

        EzyUdpSettingBuilder udpSettingBuilder = new EzyUdpSettingBuilder()
            .active(true);

        EzySimpleSessionManagementSetting sessionManagementSetting = new EzySessionManagementSettingBuilder()
            .sessionMaxIdleTimeInSecond(15)
            .sessionMaxWaitingTimeInSecond(15)
            .build();
        sessionManagementSetting.init();

        EzySimpleSettings settings = new EzySettingsBuilder()
            .debug(true)
            .socket(socketSettingBuilder.build())
            .udp(udpSettingBuilder.build())
            .zone(zoneSettingBuilder.build())
            .sessionManagement(sessionManagementSetting)
            .addEventController(
                EzyEventType.SERVER_INITIALIZING,
                ServerInitializingController.class
            )
            .addEventController(
                EzyEventType.CLIENT_HANDSHAKE,
                ClientHandshakeController.class
            )
            .build();

        EzyEmbeddedServer server = EzyEmbeddedServer.builder()
            .settings(settings)
            .build();
        server.start();
    }

    public static class DecoratedPluginEntryLoader extends PluginEntryLoader {

        @Override
        public EzyPluginEntry load() {
            return new PluginEntry() {
                private String getPluginPath() {
                    Path pluginPath = Paths.get("hello-world-plugin");
					if (!Files.exists(pluginPath)) {
						pluginPath = Paths.get("../hello-world-plugin");
					}
                    return pluginPath.toString();
                }

                @Override
                protected String getConfigFile(EzyPluginSetting setting) {
                    return Paths.get(getPluginPath(), "config", "config.properties")
                        .toString();
                }
            };
        }
    }

    public static class DecoratedAppEntryLoader extends AppEntryLoader {

        @Override
        public EzyAppEntry load() {
            return new AppEntry() {
                private String getAppPath() {
                    Path pluginPath = Paths.get("hello-world-entry");
					if (!Files.exists(pluginPath)) {
						pluginPath = Paths.get("../hello-world-entry");
					}
                    return pluginPath.toString();
                }

                @Override
                protected String getConfigFile(EzyAppSetting setting) {
                    return Paths.get(getAppPath(), "config", "config.properties")
                        .toString();
                }
            };
        }
    }
}