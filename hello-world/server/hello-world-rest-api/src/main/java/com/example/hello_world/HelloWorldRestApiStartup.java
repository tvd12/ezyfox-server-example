package com.example.hello_world;

import com.tvd12.ezyhttp.server.boot.EzyHttpApplicationBootstrap;

public class HelloWorldRestApiStartup {

    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(HelloWorldRestApiStartup.class);
    }
}
