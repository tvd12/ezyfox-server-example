package org.youngmonkeys.ezyfox_server_spring_example.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.youngmonkeys.ezyfox_server_spring_example.app.constant.Commands;
import org.youngmonkeys.ezyfox_server_spring_example.app.constant.Errors;
import org.youngmonkeys.ezyfox_server_spring_example.app.request.GoRequest;
import org.youngmonkeys.ezyfox_server_spring_example.app.request.HelloRequest;
import org.youngmonkeys.ezyfox_server_spring_example.app.response.HelloResponse;
import org.youngmonkeys.ezyfox_server_spring_example.app.service.GreetingService;
import org.youngmonkeys.ezyfox_server_spring_example.common.model.SocketUserModel;
import org.youngmonkeys.ezyfox_server_spring_example.common.service.SocketUserService;

@Setter
@EzyRequestController
public class GreetRequestController {

    @EzyAutoBind
    private EzyResponseFactory responseFactory;

    @EzyAutoBind
    private GreetingService greetingService;

    @EzyAutoBind
    private ApplicationContext applicationContext;

    @EzyDoHandle(Commands.HELLO)
    public void sayHello(EzyUser user, HelloRequest request) {
        String nickName = request.getNickName();
        if (EzyStrings.isNoContent(nickName)) {
            throw new EzyBadRequestException(Errors.UNKNOWN, "invalid nick name");
        }
        SocketUserService socketUserService = applicationContext
            .getBean(SocketUserService.class);
        SocketUserModel socketUser = socketUserService.getSocketUserByUsername(
            nickName
        );
        String displayName = socketUser != null
            ? socketUser.getDisplayName()
            : nickName;
        responseFactory.newObjectResponse()
            .command(Commands.HELLO)
            .data(new HelloResponse(greetingService.hello(displayName)))
            .user(user)
            .execute();
    }

    @EzyDoHandle(Commands.GO)
    public void sayGo(EzyUser user, GoRequest request) {
        if (EzyStrings.isNoContent(request.getNickName())) {
            throw new EzyBadRequestException(Errors.UNKNOWN, "invalid nick name");
        }
        responseFactory.newObjectResponse()
            .command(Commands.GO)
            .data(new HelloResponse(greetingService.go(request.getNickName())))
            .user(user)
            .execute();
    }
}
