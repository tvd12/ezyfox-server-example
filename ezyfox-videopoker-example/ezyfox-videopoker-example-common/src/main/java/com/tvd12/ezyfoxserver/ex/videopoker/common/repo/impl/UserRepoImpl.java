package com.tvd12.ezyfoxserver.ex.videopoker.common.repo.impl;

import com.tvd12.ezyfoxserver.ex.videopoker.common.data.User;
import com.tvd12.ezyfoxserver.ex.videopoker.common.repo.UserRepo;
import com.tvd12.ezyfoxserver.morphia.repository.EzyDatastoreRepository;

public class UserRepoImpl
		extends EzyDatastoreRepository<Long, User> 
		implements UserRepo {

	@Override
	public User findByUsername(String username) {
		return findByField("username", username);
	}
	
	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}
	
	
}
