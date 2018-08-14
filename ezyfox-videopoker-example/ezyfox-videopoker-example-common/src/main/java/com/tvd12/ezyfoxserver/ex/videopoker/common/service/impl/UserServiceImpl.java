package com.tvd12.ezyfoxserver.ex.videopoker.common.service.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.ex.videopoker.common.constant.Entities;
import com.tvd12.ezyfoxserver.ex.videopoker.common.constant.LockKeys;
import com.tvd12.ezyfoxserver.ex.videopoker.common.data.User;
import com.tvd12.ezyfoxserver.ex.videopoker.common.service.UserService;
import com.tvd12.ezyfoxserver.ex.videopoker.common.service.HazelcastMapHasMaxIdService;
import com.tvd12.ezyfoxserver.function.EzyApply;

import lombok.Setter;

@Setter
@EzySingleton("userService")
public class UserServiceImpl 
		extends HazelcastMapHasMaxIdService<String, User> 
		implements UserService {

	@Override
	public void saveUser(User user) {
		set(user.getUsername(), user);
		
	}
	
	@Override
	public User getUser(String username) {
		return get(username);
	}
	
	@Override
	public User checkAndNewIfNotExistsUser(
			String username, AtomicBoolean exists, EzyApply<User> applier) {
		User user = getUser(username);
		if(user != null) return user;
		String lockKey = username + LockKeys.NEW_USER_SUFFIX;
		user = lockUpdateAndGet(lockKey, () -> {
			User cuser = getUser(username);
			if(cuser != null) return cuser;
			exists.set(false);
			User nuser = newUser(username);
			applier.apply(nuser);
			saveUser(nuser);
			return nuser;
		});
		return user;
	}
	
	private User newUser(String username) {
		User user = new User();
		user.setId(newId(Entities.CHAT_USER));
		user.setUsername(username);
		return user;
	}
	
	@Override
	protected String getMapName() {
		return Entities.CHAT_USER;
	}
}
