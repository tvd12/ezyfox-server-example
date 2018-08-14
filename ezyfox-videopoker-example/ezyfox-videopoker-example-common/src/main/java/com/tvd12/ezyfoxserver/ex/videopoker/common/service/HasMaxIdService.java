package com.tvd12.ezyfoxserver.ex.videopoker.common.service;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.hazelcast.service.EzyMaxIdService;

import lombok.Setter;

@Setter
public class HasMaxIdService {

	@EzyAutoBind
	protected EzyMaxIdService maxIdService;
	
	protected long newId(String key) {
		return maxIdService.incrementAndGet(key);
	}
	
}
