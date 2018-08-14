package com.tvd12.ezyfoxserver.ex.videopoker.common.mapstore;

import java.util.Map;

import com.tvd12.ezyfoxserver.database.annotation.EzyMapstore;
import com.tvd12.ezyfoxserver.ex.videopoker.common.constant.Entities;
import com.tvd12.ezyfoxserver.ex.videopoker.common.data.User;
import com.tvd12.ezyfoxserver.ex.videopoker.common.repo.UserRepo;
import com.tvd12.ezyfoxserver.ex.videopoker.common.repo.impl.UserRepoImpl;
import com.tvd12.ezyfoxserver.hazelcast.mapstore.EzyMongoDatastoreMapstore;

@EzyMapstore(Entities.CHAT_USER)
public class UserMapstore extends EzyMongoDatastoreMapstore<String, User> {

	private UserRepo repo;
	
	@Override
	public void store(String key, User value) {
		repo.save(value);
	}

	@Override
	public void storeAll(Map<String, User> map) {
		repo.save(map.values());
	}
	
	@Override
	public User load(String key) {
		return repo.findByField("username", key);
	}

	@Override
	public void postInit() {
		UserRepoImpl repo = new UserRepoImpl();
		repo.setDatastore(datastore);
		this.repo = repo;
	}
	
}
