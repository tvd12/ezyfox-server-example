package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;

public class DefaultClientConfig {

    public EzyClientConfig get(int count) {
        return EzyClientConfig.builder()
            .clientName("hello-word-" + count)
            .zoneName("example")
            .build();
    }
}
