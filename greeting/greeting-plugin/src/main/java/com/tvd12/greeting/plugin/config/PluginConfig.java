package com.tvd12.greeting.plugin.config;

import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import lombok.Data;

@Data
@EzyPropertiesBean(prefix = "application")
public class PluginConfig {
    private String welcomePrefix;
}
