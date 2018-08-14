package com.tvd12.ezyfoxserver.ex.videopoker.common.service;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tvd12.ezyfoxserver.ex.videopoker.common.data.User;
import com.tvd12.ezyfoxserver.function.EzyApply;

public interface UserService {

	void saveUser(User user);
	
	User getUser(String username);
	
	User checkAndNewIfNotExistsUser(
			String username, AtomicBoolean exists, EzyApply<User> applier);
	
}
