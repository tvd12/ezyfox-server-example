package com.tvd12.example.lucky_wheel.controller;

import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@EzyRequestController
public class RequestController {
    private final EzyResponseFactory responseFactory;

    @EzyDoHandle("spin")
    public void spin(EzySession session) {
        int result = ThreadLocalRandom.current().nextInt(8);
        responseFactory.newObjectResponse()
            .command("spin")
            .param("result", result)
            .session(session)
            .execute();
    }
}
