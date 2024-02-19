package org.youngmonkeys.simple_chat.plugin.config;

import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import lombok.Data;

@Data
@EzyPropertiesBean(prefix = "application")
public class PluginConfig {
    private String welcomePrefix;
}
