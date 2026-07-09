package com.example.hello_world.handler;

import com.example.hello_world.common.Greeting;
import com.example.hello_world.constant.ExampleEventType;
import com.example.hello_world.event.UserGreetEvent;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import lombok.Setter;

import static com.example.hello_world.constant.Commands.GREET;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyRequestListener(GREET)
public class GreetRequestHandler
    extends ClientRequestHandler
    implements EzyDataBinding {

    private String who;

    @EzyAutoBind
    private Greeting greeting;

    @EzyAutoBind
    private EzyServerContext serverContext;

    @Override
    protected void execute() throws EzyBadRequestException {
        responseFactory.newObjectResponse()
            .command(GREET)
            .param("message", greeting.greet(who))
            .session(session)
            .execute();
        serverContext.broadcast(
            ExampleEventType.GREET,
            new UserGreetEvent(who),
            true
        );
    }
}
