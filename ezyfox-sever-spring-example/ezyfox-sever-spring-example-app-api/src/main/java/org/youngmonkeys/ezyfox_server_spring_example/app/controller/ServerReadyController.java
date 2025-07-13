package org.youngmonkeys.ezyfox_server_spring_example.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.SERVER_READY;

@EzySingleton
@EzyEventHandler(SERVER_READY) // refer EzyEventType
public class ServerReadyController
    extends EzyAbstractAppEventController<EzyServerReadyEvent> {

    @Override
    public void handle(EzyAppContext ctx, EzyServerReadyEvent event) {
        logger.info("ezyfox-sever-spring-example app: fire custom app ready");
    }
}
