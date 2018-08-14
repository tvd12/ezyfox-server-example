package com.tvd12.ezyfoxserver.ex.videopoker.common.service;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.hazelcast.service.EzyBeanSimpleHazelcastMapService;
import com.tvd12.ezyfoxserver.hazelcast.service.EzyMaxIdService;

import lombok.Setter;

public abstract class HazelcastMapHasMaxIdService<K,V> 
		extends EzyBeanSimpleHazelcastMapService<K, V> {

	@EzyAutoBind
	@Setter
	protected EzyMaxIdService maxIdService;
	
	protected long newId(String key) {
		return maxIdService.incrementAndGet(key);
	}
	
}
