package com.example.hello_world.handler;

import com.example.hello_world.common.Greeting;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import lombok.Setter;

import static com.example.hello_world.constant.Commands.UDP_GREET;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyRequestListener(UDP_GREET)
public class UdpGreetRequestHandler
    extends ClientRequestHandler
    implements EzyDataBinding {

    private String who;

    @EzyAutoBind
    private Greeting greeting;

    @Override
    protected void execute() throws EzyBadRequestException {
        responseFactory.newObjectResponse()
            .command(UDP_GREET)
            .udpTransport()
            .param("message", greeting.greet(who))
            .param("negativeFixInt1", (byte) -1)
            .param("maxByte", Byte.MAX_VALUE)
            .param("minByte", Byte.MIN_VALUE)
            .param("maxShort", Short.MAX_VALUE)
            .param("minShort", Short.MIN_VALUE)
            .param("maxInt", Integer.MAX_VALUE)
            .param("minInt", Integer.MIN_VALUE)
            .param("maxLong", Long.MAX_VALUE)
            .param("minLong", Long.MIN_VALUE)
            .param("maxFloat", Float.MAX_VALUE)
            .param("minFloat", Float.MIN_VALUE)
            .param("maxDouble", Double.MAX_VALUE)
            .param("minDouble", Double.MIN_VALUE)
            .session(session)
            .execute();
    }
}
