package org.youngmonkeys.ezyfox_server_spring_example.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class CommonService {

    public String hello(String prefix, String who) {
        return prefix + who + "!";
    }

    public String go(String prefix, String who) {
        return prefix + who + "!";
    }
}
