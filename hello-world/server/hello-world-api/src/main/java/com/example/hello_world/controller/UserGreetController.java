package com.example.hello_world.controller;

import com.example.hello_world.event.UserGreetEvent;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;

@EzyEventHandler(event = "com.example.hello_world.constant.ExampleEventType.GREET")
public class UserGreetController
    extends EzyAbstractAppEventController<UserGreetEvent> {

    @Override
    public void handle(EzyAppContext ctx, UserGreetEvent event) {
        logger.info("handle event: {}", event);
    }
}
