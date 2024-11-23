package com.tvd12.greeting.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserExitedAppEvent;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_EXITED_APP;

@EzySingleton
@EzyEventHandler(USER_EXITED_APP) // refer EzyEventType
public class UserExitedAppController
    extends EzyAbstractAppEventController<EzyUserExitedAppEvent> {

    @Override
    public void handle(
        EzyAppContext ctx,
        EzyUserExitedAppEvent event
    ) {
        logger.info(
            "greeting app: fire user {} exited app",
            event.getUser()
        );
    }
}
