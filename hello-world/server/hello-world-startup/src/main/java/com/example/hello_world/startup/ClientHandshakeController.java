package com.example.hello_world.startup;

import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractServerEventController;
import com.tvd12.ezyfoxserver.event.EzyHandshakeEvent;
import com.tvd12.ezyfoxserver.event.EzyServerInitializingEvent;

public class ClientHandshakeController
    extends EzyAbstractServerEventController<EzyHandshakeEvent> {

    @Override
    public void handle(EzyServerContext ctx, EzyHandshakeEvent event) {
        System.out.println("Handle handshake from session: " + event.getSession());
    }
}
