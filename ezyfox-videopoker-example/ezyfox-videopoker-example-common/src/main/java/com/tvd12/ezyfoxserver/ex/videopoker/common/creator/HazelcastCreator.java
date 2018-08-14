package com.tvd12.ezyfoxserver.ex.videopoker.common.creator;

import org.mongodb.morphia.Datastore;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.mongodb.client.MongoDatabase;
import com.tvd12.ezyfoxserver.function.EzyCreation;
import com.tvd12.ezyfoxserver.hazelcast.EzyMongoDatastoreHazelcastFactory;
import com.tvd12.ezyfoxserver.hazelcast.mapstore.EzyMapstoresFetcher;
import com.tvd12.ezyfoxserver.hazelcast.mapstore.EzySimpleMapstoresFetcher;
import com.tvd12.ezyfoxserver.hazelcast.util.EzyHazelcastConfigs;

public class HazelcastCreator implements EzyCreation<HazelcastInstance> {

	private String filePath;
	private Datastore datastore;
	private MongoDatabase database;
	
	public HazelcastCreator filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	
	public HazelcastCreator datastore(Datastore datastore) {
		this.datastore = datastore;
		return this;
	}
	
	public HazelcastCreator database(MongoDatabase database) {
		this.database = database;
		return this;
	}
	
	@Override
	public HazelcastInstance create() {
		EzyMongoDatastoreHazelcastFactory factory = 
				new EzyMongoDatastoreHazelcastFactory();
		factory.setDatabase(database);
		factory.setDatastore(datastore);
		factory.setMapstoresFetcher(newMapstoresFetcher());
		return factory.newHazelcast(newConfig());
	}
	
	private Config newConfig() {
		return EzyHazelcastConfigs.newXmlConfigBuilder(filePath);
	}
	
	private EzyMapstoresFetcher newMapstoresFetcher() {
		return EzySimpleMapstoresFetcher.builder()
				.scan("vn.team.freechat.mapstore")
				.scan("com.tvd12.ezyfox.ex.videopoker.common.mapstore")
				.scan("vn.team.freechat.plugin.mapstore")
				.build();
	}
	
}
