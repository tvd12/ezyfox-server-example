/**
 * 
 */
package com.tvd12.ezyfoxserver.plugin.auth;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.mongodb.MongoClient;
import com.tvd12.ezyfoxserver.bean.EzyBeanContext;
import com.tvd12.ezyfoxserver.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfoxserver.bean.EzySingletonFactory;
import com.tvd12.ezyfoxserver.binding.EzyBindingContext;
import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.binding.EzyUnmarshaller;
import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntry;
import com.tvd12.ezyfoxserver.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfoxserver.mongodb.loader.EzyPropertiesMongoClientLoader;
import com.tvd12.ezyfoxserver.morphia.EzyDataStoreBuilder;
import com.tvd12.ezyfoxserver.morphia.bean.EzyMorphiaRepositories;
import com.tvd12.ezyfoxserver.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.setting.EzyPluginSetting;
import com.tvd12.ezyfoxserver.util.EzyMapBuilder;

/**
 * @author tavandung12
 *
 */
public class EzyAuthPluginEntry extends EzyAbstractPluginEntry {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void config(EzyPluginContext ctx) {
		getLogger().info("auth plugin: config");
		EzyBeanContext beanContext = createBeanContext(ctx);
		EzySingletonFactory singletonFactory = beanContext.getSingletonFactory();
		
		List<Object> eventHandlers = beanContext.getSingletons(
				EzyMapBuilder.mapBuilder()
					.put("type", "EVENT_HANDLER")
					.build()
		);
		
		for(Object handler : eventHandlers) {
			Map<String,String> props = singletonFactory.getProperties(handler);
			String eventName = props.get("name");
			ctx.get(EzyAddEventController.class)
				.add(EzyEventType.valueOf(eventName), (EzyEventController) handler);
		}
		
	}

	@Override
	public void start() throws Exception {
		getLogger().info("auth plugin: start");
	}

	@Override
	public void destroy() {
		getLogger().info("auth plugin: destry");
	}
	private EzyBeanContext createBeanContext(EzyPluginContext context) {
    	EzyBindingContext bindingContext = createBindingContext();
    	EzyMarshaller marshaller = bindingContext.newMarshaller();
    	EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
    	EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
    			.addSingleton("pluginContext", context)
    			.addSingleton("marshaller", marshaller)
    			.addSingleton("unmarshaller", unmarshaller)
    			.scan("com.tvd12.ezyfoxserver.plugin.auth");
    	
    	MongoClient mongoClient = loadMongoClient();
    	Datastore datastore = newDataStore(mongoClient);
    	
    	beanContextBuilder.addSingleton("mongoClient", mongoClient);
    	beanContextBuilder.addSingleton("datastore", datastore);
    	
    	Map<Class<?>, Object> additionalRepo = implementMongoRepo(datastore);
    	for(Class<?> repoType : additionalRepo.keySet()) {
    		beanContextBuilder.addSingleton(
    				EzyClasses.getVariableName(repoType), additionalRepo.get(repoType));
    	}
    	
    	return beanContextBuilder.build();
    }
    
    private Map<Class<?>, Object> implementMongoRepo(Datastore datastore) {
    	return EzyMorphiaRepositories.newRepositoriesImplementer()
    			.scan("com.tvd12.ezyfoxserver.plugin.auth.repo")
    			.implement(datastore);
    }
    
    private Datastore newDataStore(MongoClient mongoClient) {
    	return EzyDataStoreBuilder.dataStoreBuilder()
    			.mongoClient(mongoClient)
    			.databaseName("test")
    			.scan("com.tvd12.ezyfoxserver.plugin.auth.data")
    			.build();
    }
    
    private MongoClient loadMongoClient() {
    	return new EzyPropertiesMongoClientLoader()
    			.property(EzyMongoClientLoader.HOST, "127.0.0.1")
    			.property(EzyMongoClientLoader.PORT, "27017")
    			.property(EzyMongoClientLoader.DATABASE, "test")
    			.property(EzyMongoClientLoader.USERNAME, "root")
    			.property(EzyMongoClientLoader.PASSWORD, "123456")
    			.load();
    }
    
    private EzyBindingContext createBindingContext() {
    	EzyBindingContext bindingContext = EzyBindingContext.builder()
    			.scan("com.tvd12.ezyfoxserver.plugin.auth")
    			.build(); 
    	return bindingContext;
    }
    
    protected EzyPluginSetting getSetting(EzyPluginContext context) {
    	return context.getPlugin().getSetting();
    }

}