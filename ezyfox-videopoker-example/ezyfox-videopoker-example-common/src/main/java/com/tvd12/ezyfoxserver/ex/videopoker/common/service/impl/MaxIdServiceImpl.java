package com.tvd12.ezyfoxserver.ex.videopoker.common.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.ex.videopoker.common.service.MaxIdService;
import com.tvd12.ezyfoxserver.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfoxserver.hazelcast.service.EzySimpleMaxIdService;

@EzySingleton("maxIdService")
public class MaxIdServiceImpl 
		extends EzySimpleMaxIdService
		implements MaxIdService {

	@EzyAutoBind("hzInstance")
	public MaxIdServiceImpl(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}

	@EzyAutoBind
	@Override
	public void setMapTransactionFactory(EzyMapTransactionFactory mapTransactionFactory) {
		super.setMapTransactionFactory(mapTransactionFactory);
	}

}
