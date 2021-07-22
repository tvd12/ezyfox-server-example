package org.youngmonkeys.example.push_message.gateway.api;

import com.tvd12.ezyhttp.core.boot.EzyHttpApplicationBootstrap;

public class PushMessageApiGatewayStartup {
    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(PushMessageApiGatewayStartup.class);
    }
}
