package com.example.hello_world.handler;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.EzyApplication;
import com.tvd12.ezyfoxserver.wrapper.EzyAppUserManager;
import lombok.Setter;

import static com.example.hello_world.constant.Commands.UDP_BROADCAST_MESSAGE;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyRequestListener(UDP_BROADCAST_MESSAGE)
public class UdpBroadcastMessageHandler
    extends ClientRequestHandler
    implements EzyDataBinding {

    private String message;

    @Override
    protected void execute() throws EzyBadRequestException {
        EzyApplication app = appContext.getApp();
        EzyAppUserManager userManager = app.getUserManager();
        responseFactory.newObjectResponse()
            .udpOrTcpTransport()
            .command(UDP_BROADCAST_MESSAGE)
            .param("message", message)
            .users(userManager.getUserList())
            .execute();
    }
}
