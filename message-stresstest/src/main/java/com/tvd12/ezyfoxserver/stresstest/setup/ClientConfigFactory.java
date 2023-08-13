package com.tvd12.ezyfoxserver.stresstest.setup;

import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;

public class ClientConfigFactory {

    public EzyClientConfig.Builder newConfigBuilder(int count) {
        return EzyClientConfig.builder()
            .clientName("client-" + count)
            .zoneName("example");
    }
}
