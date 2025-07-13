package org.youngmonkeys.ezyfox_server_spring_example.plugin.service;

import org.youngmonkeys.ezyfox_server_spring_example.common.service.CommonService;
import org.youngmonkeys.ezyfox_server_spring_example.plugin.config.PluginConfig;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class WelcomeService {

    @EzyAutoBind
    private PluginConfig config;

    @EzyAutoBind
    private CommonService commonService;

    public String welcome(String username) {
        return config.getWelcomePrefix() + " " + username + "!";
    }
}
