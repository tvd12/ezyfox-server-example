package com.example.hello_world.startup;

import com.tvd12.ezyfoxserver.command.EzyAddExceptionHandler;
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractServerEventController;
import com.tvd12.ezyfoxserver.event.EzyServerInitializingEvent;

public class ServerInitializingController
    extends EzyAbstractServerEventController<EzyServerInitializingEvent> {

    @Override
    public void handle(EzyServerContext ctx, EzyServerInitializingEvent event) {
        System.out.println("Server's initializing");
        ctx.cmd(EzyAddExceptionHandler.class).add((thread, exception) ->
            exception.printStackTrace()
        );
    }
}
