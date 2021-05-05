package com.tvd12.mmorpg.stresstest.client;

import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;

public class DefaultClientConfig {

	public EzyClientConfig get(int count) {
		EzyClientConfig config = EzyClientConfig.builder()
				.clientName("mmorpg-" + count)
				.zoneName("mmorpg")
				.build();
		return config;
	}
	
}
