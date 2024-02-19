package org.youngmonkeys.simple_chat.app.service;

import org.youngmonkeys.simple_chat.app.config.AppConfig;
import org.youngmonkeys.simple_chat.common.service.CommonService;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class GreetingService {

    @EzyAutoBind
    private AppConfig appConfig;

    @EzyAutoBind
    private CommonService commonService;

    public String hello(String nickName) {
        return appConfig.getHelloPrefix() + " " + nickName + "!";
    }

    public String go(String nickName) {
        return appConfig.getGoPrefix() + " " + nickName + "!";
    }
}
