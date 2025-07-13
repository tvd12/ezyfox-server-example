package org.youngmonkeys.ezyfox_server_spring_example.plugin.controller;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.SERVER_READY;

@EzySingleton
@EzyEventHandler(SERVER_READY)
public class ServerReadyController
    extends EzyAbstractPluginEventController<EzyServerReadyEvent> {

    @Override
    public void handle(EzyPluginContext ctx, EzyServerReadyEvent event) {
        logger.info("ezyfox-sever-spring-example plugin: fire custom app ready");
    }
}
