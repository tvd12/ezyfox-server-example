package com.tvd12.ezyfoxserver.chat.repo;

import org.bson.types.ObjectId;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.mongodb.EzyMongoRepository;
import com.tvd12.ezyfoxserver.chat.data.EzyChatUser;
@EzyAutoImpl
public interface EzyChatUserRepo extends EzyMongoRepository<ObjectId, EzyChatUser> {

}
