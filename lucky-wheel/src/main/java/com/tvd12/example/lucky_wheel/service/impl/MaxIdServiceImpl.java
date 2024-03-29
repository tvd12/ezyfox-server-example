package com.tvd12.example.lucky_wheel.service.impl;

import com.tvd12.example.lucky_wheel.service.MaxIdService;
import com.tvd12.ezydata.database.repository.EzyMaxIdRepository;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Setter;

@Setter
@EzySingleton("maxIdService")
public class MaxIdServiceImpl implements MaxIdService {
	
	@EzyAutoBind
	private EzyMaxIdRepository maxIdRepository;
	
	@Override
	public void loadAll() {
		//
	}
	
	@Override
	public Long incrementAndGet(String key) {
		return maxIdRepository.incrementAndGet(key);
	}
	
	@Override
	public Long incrementAndGet(String key, int delta) {
		return maxIdRepository.incrementAndGet(key, delta);
	}
}
