package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;

public class DefaultClientConfig {

	public EzyClientConfig get(int count) {
		EzyClientConfig config = EzyClientConfig.builder()
				.clientName("hello-word-" + count)
				.zoneName("example")
				.build();
		return config;
	}
	
}
