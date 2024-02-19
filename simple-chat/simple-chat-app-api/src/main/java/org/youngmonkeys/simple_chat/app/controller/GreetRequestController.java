package org.youngmonkeys.simple_chat.app.controller;

import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;
import org.youngmonkeys.simple_chat.app.constant.Commands;
import org.youngmonkeys.simple_chat.app.constant.Errors;
import org.youngmonkeys.simple_chat.app.request.GoRequest;
import org.youngmonkeys.simple_chat.app.request.HelloRequest;
import org.youngmonkeys.simple_chat.app.response.HelloResponse;
import org.youngmonkeys.simple_chat.app.service.GreetingService;
import org.youngmonkeys.simple_chat.common.service.MessageService;

import static org.youngmonkeys.simple_chat.common.constant.CommonConstants.KEY_DB_USER_ID;

@EzyRequestController
@AllArgsConstructor
public class GreetRequestController {

    private final GreetingService greetingService;
    private final MessageService messageService;
    private final EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.HELLO)
    public void sayHello(EzyUser user, HelloRequest request) {
        if (EzyStrings.isNoContent(request.getNickName())) {
            throw new EzyBadRequestException(Errors.UNKNOWN, "invalid nick name");
        }
        String message = greetingService.hello(request.getNickName());
        messageService.addMessage(
            user.getProperty(KEY_DB_USER_ID),
            0,
            message
        );
        responseFactory.newObjectResponse()
            .command(Commands.HELLO)
            .data(new HelloResponse(message))
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
