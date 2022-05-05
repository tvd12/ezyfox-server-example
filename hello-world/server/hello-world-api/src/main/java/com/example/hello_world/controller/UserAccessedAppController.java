package com.example.hello_world.controller;

import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserAccessedAppEvent;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_ACCESSED_APP;

@EzyEventHandler(USER_ACCESSED_APP) // refer EzyEventType
public class UserAccessedAppController
    extends EzyAbstractAppEventController<EzyUserAccessedAppEvent> {

    @Override
    public void handle(EzyAppContext ctx, EzyUserAccessedAppEvent event) {
        logger.info(
            "user: {} has just accessed app: {}",
            event.getUser(),
            ctx.getApp()
        );
    }
}
