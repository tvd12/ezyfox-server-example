package org.youngmonkeys.ezyfox_server_spring_example;

import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.youngmonkeys.ezyfox_server_spring_example.app.AppEntry;
import org.youngmonkeys.ezyfox_server_spring_example.app.AppEntryLoader;
import org.youngmonkeys.ezyfox_server_spring_example.plugin.PluginEntry;
import org.youngmonkeys.ezyfox_server_spring_example.plugin.PluginEntryLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class ApplicationStartup {

    public static final String ZONE_APP_NAME = "ezyfox-sever-spring-example";

    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = SpringApplication
            .run(ApplicationStartup.class);

        EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
            .name(ZONE_APP_NAME)
            .addListenEvent(EzyEventType.USER_LOGIN)
            .entryLoader(DecoratedPluginEntryLoader.class)
            .entryLoaderArgs(new Object[] {applicationContext});

        EzyAppSettingBuilder appSettingBuilder = new EzyAppSettingBuilder()
            .name(ZONE_APP_NAME)
            .entryLoader(DecoratedAppEntryLoader.class)
            .entryLoaderArgs(new Object[] {applicationContext});

        EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
            .name(ZONE_APP_NAME)
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

    @AllArgsConstructor
    public static class DecoratedPluginEntryLoader extends PluginEntryLoader {

        private final ApplicationContext applicationContext;

        @Override
        public EzyPluginEntry load() {
            return new PluginEntry() {

                @Override
                protected String getConfigFile(EzyPluginSetting setting) {
                    return Paths.get(getPluginPath(setting), "config", "config.properties")
                        .toString();
                }

                private String getPluginPath(EzyPluginSetting setting) {
                    Path pluginPath = Paths.get("ezyfox-sever-spring-example-plugin");
                    if (!Files.exists(pluginPath)) {
                        pluginPath = Paths.get("../ezyfox-sever-spring-example-plugin");
                    }
                    return pluginPath.toString();
                }

                @Override
                protected void setupBeanContext(
                    EzyPluginContext context,
                    EzyBeanContextBuilder builder
                ) {
                    super.setupBeanContext(context, builder);
                    builder.addSingleton(applicationContext);
                }
            };
        }
    }

    @AllArgsConstructor
    public static class DecoratedAppEntryLoader extends AppEntryLoader {

        private final ApplicationContext applicationContext;

        @Override
        public EzyAppEntry load() {
            return new AppEntry() {

                @Override
                protected String getConfigFile(EzyAppSetting setting) {
                    return Paths.get(getAppPath(), "config", "config.properties")
                        .toString();
                }

                private String getAppPath() {
                    Path pluginPath = Paths.get("ezyfox-sever-spring-example-app-entry");
                    if (!Files.exists(pluginPath)) {
                        pluginPath = Paths.get("../ezyfox-sever-spring-example-app-entry");
                    }
                    return pluginPath.toString();
                }

                @Override
                protected void setupBeanContext(
                    EzyAppContext context,
                    EzyBeanContextBuilder builder
                ) {
                    super.setupBeanContext(context, builder);
                    builder.addSingleton(applicationContext);
                }
            };
        }
    }
}
