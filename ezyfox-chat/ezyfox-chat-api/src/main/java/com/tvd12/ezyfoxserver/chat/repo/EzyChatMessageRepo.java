package com.tvd12.ezyfoxserver.chat.repo;

import org.bson.types.ObjectId;

import com.tvd12.ezyfoxserver.annotation.EzyAutoImpl;
import com.tvd12.ezyfoxserver.chat.data.EzyChatMessage;
import com.tvd12.ezyfoxserver.mongodb.EzyMongoRepository;

@EzyAutoImpl
public interface EzyChatMessageRepo 
	extends EzyMongoRepository<ObjectId, EzyChatMessage> {

}
