package com.tvd12.ezyfoxserver.plugin.auth.repo;

import org.bson.types.ObjectId;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfoxserver.plugin.auth.data.ChatUser;

@EzyAutoImpl
public interface ChatUserRepo 
		extends EzyMongoRepository<ObjectId,ChatUser>   {
	
}
