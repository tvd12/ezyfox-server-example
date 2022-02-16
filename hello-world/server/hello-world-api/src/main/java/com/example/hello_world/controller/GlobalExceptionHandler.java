package com.example.hello_world.controller;

import com.example.hello_world.exception.BadWhoRequestException;
import com.example.hello_world.exception.InvalidChatRequestException;
import com.tvd12.ezyfox.core.annotation.EzyExceptionHandler;
import com.tvd12.ezyfox.core.annotation.EzyRequestData;
import com.tvd12.ezyfox.core.annotation.EzyTryCatch;
import com.tvd12.ezyfox.core.constant.EzyResponseCommands;
import com.tvd12.ezyfox.util.EzyEntityObjects;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserSessionEvent;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyExceptionHandler
public class GlobalExceptionHandler extends EzyLoggable {

    private final EzyResponseFactory responseFactory;
    
    @EzyTryCatch(InvalidChatRequestException.class)
    public void handle(
        InvalidChatRequestException e,
        EzyUserSessionEvent event,
        String command,
        Object request
    ) {
        logger.info("invalid chat request, command: {}, data: {}", command, request, e);
        responseFactory.newObjectResponse()
            .command(EzyResponseCommands.ERROR)
            .data(EzyEntityObjects.newObject("request", "invalid"))
            .session(event.getSession())
            .execute();
    }
    
    @EzyTryCatch(BadWhoRequestException.class)
    public void handle(
            BadWhoRequestException e,
            EzyUser user,
            String cmd, 
            Object data) {
        logger.error("try cath BadWhoRequestException, cmd: {}, data: {}", cmd, data, e);
        responseFactory.newObjectResponse()
            .command(EzyResponseCommands.ERROR)
            .data(EzyEntityObjects.newObject("who", "invalid"))
            .user(user)
            .execute();
    }
    
    @EzyTryCatch(IllegalArgumentException.class)
    public void handle(
            IllegalArgumentException e,
            EzySession session,
            String cmd, 
            @EzyRequestData Object data) {
        logger.error("try cath IllegalArgumentException, cmd: {}, data: {}", cmd, data, e);
        responseFactory.newObjectResponse()
            .command(EzyResponseCommands.ERROR)
            .data(EzyEntityObjects.newObject("arguments", "invalid"))
            .session(session)
            .execute();
    }
    
    @EzyTryCatch(Exception.class)
    public void handle(
            Exception e,
            String cmd, 
            @EzyRequestData Object data) throws Exception {
        throw e;
    }
}
