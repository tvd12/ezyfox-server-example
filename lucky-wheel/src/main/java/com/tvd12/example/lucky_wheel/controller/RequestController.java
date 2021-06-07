package com.tvd12.example.lucky_wheel.controller;

import com.tvd12.example.lucky_wheel.service.WheelService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyRequestController
public class RequestController {
    private final WheelService wheelService;
    private final EzyResponseFactory responseFactory;

    @EzyDoHandle("spin")
    public void spin(EzySession session) {
        int result = wheelService.spin();
        responseFactory.newObjectResponse()
            .command("spin")
            .param("result", result)
            .session(session)
            .execute();
    }
}
