package com.tvd12.example.lucky_wheel;

import com.tvd12.ezyhttp.core.boot.EzyHttpApplicationBootstrap;
import com.tvd12.ezyhttp.server.core.asm.ExceptionHandlerImplementer;
import com.tvd12.ezyhttp.server.core.asm.RequestHandlerImplementer;

public class ClientStartup {
    public static void main(String[] args) throws Exception {
        RequestHandlerImplementer.setDebug(true);
        ExceptionHandlerImplementer.setDebug(true);
        EzyHttpApplicationBootstrap.start(ClientStartup.class);
    }
}
