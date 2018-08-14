package com.tvd12.ezyfoxserver.ex.videopoker.common.creator;

import com.mongodb.MongoClient;
import com.tvd12.ezyfoxserver.function.EzyCreation;
import com.tvd12.ezyfoxserver.mongodb.loader.EzyFileMongoClientLoader;

public class MongoCreator implements EzyCreation<MongoClient> {

	private String filePath;
	
	public MongoCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	@Override
	public MongoClient create() {
		return EzyFileMongoClientLoader.load(filePath);
	}
	
}
