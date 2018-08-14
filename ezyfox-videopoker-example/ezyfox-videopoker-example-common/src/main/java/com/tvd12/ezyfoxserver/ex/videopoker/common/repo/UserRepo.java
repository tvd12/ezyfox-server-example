package com.tvd12.ezyfoxserver.ex.videopoker.common.repo;

import com.tvd12.ezyfoxserver.ex.videopoker.common.data.User;
import com.tvd12.ezyfoxserver.mongodb.EzyMongoRepository;

public interface UserRepo extends EzyMongoRepository<Long, User> {

	User findByUsername(String username);
	
}
