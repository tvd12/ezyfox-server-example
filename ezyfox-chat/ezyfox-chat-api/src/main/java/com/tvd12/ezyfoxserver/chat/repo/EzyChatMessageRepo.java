package com.tvd12.ezyfoxserver.chat.repo;

import org.bson.types.ObjectId;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfoxserver.chat.data.EzyChatMessage;

@EzyAutoImpl
public interface EzyChatMessageRepo 
	extends EzyMongoRepository<ObjectId, EzyChatMessage> {

}
