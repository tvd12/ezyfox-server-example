/**
 * 
 */
package com.tvd12.ezyfoxserver.chat;

import java.io.File;
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
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;
import com.tvd12.ezyfoxserver.mongodb.EzyDataStoreBuilder;
import com.tvd12.ezyfoxserver.mongodb.bean.EzyRepositoriesImplementor;
import com.tvd12.ezyfoxserver.mongodb.loader.EzyMongoClientLoader;
import com.tvd12.ezyfoxserver.mongodb.loader.EzyPropertiesMongoClientLoader;
import com.tvd12.ezyfoxserver.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.ezyfoxserver.util.EzyMapBuilder;

/**
 * @author tavandung12
 *
 */
public class EzyChatEntry extends EzyAbstractAppEntry {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void config(EzyAppContext ctx) {
		getLogger().info("Chat app config");
		EzyBeanContext beanContext = createBeanContext(ctx);
		EzySingletonFactory singletonFactory = beanContext.getSingletonFactory();
		EzyAddEventController eventControllerAdder = ctx.get(EzyAddEventController.class);
		List<Object> eventControllers = beanContext.getSingletons(
				EzyMapBuilder.newInstance()
					.put("type", "EVENT_HANDLER")
					.build()
		);
		for(Object controller : eventControllers) {
			Map<String, String> prop = singletonFactory.getProperties(controller);
			EzyEventType eventType = EzyEventType.valueOf(prop.get("name"));
			eventControllerAdder.add(eventType, (EzyEventController) controller);
			getLogger().debug("add event {} controller {}", eventType, controller);
		}
		EzyAppSetting setting = getSetting(ctx);
		File file = new File(setting.getConfigFile());
		System.out.println("\n\n file = " + file.getAbsolutePath() + "\n\n");
	}
	
    @Override
    public void start() throws Exception {
    	getLogger().info("Chat app start");
    }
    
    @Override
    public void destroy() {
    	getLogger().info("Chat app destroy");
    }
    
    private EzyBeanContext createBeanContext(EzyAppContext context) {
    	EzyBindingContext bindingContext = createBindingContext();
    	EzyMarshaller marshaller = bindingContext.newMarshaller();
    	EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
    	EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
    			.addSingleton("appContext", context)
    			.addSingleton("userManager", context.getApp().getUserManager())
    			.addSingleton("marshaller", marshaller)
    			.addSingleton("unmarshaller", unmarshaller)
    			.scan("com.tvd12.ezyfoxserver.chat");
    	
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
    	return EzyRepositoriesImplementor.newInstance()
    			.scan("com.tvd12.ezyfoxserver.chat.repo")
    			.implement(datastore);
    }
    
    private Datastore newDataStore(MongoClient mongoClient) {
    	return EzyDataStoreBuilder.newInstance()
    			.mongoClient(mongoClient)
    			.databaseName("test")
    			.scan("com.tvd12.ezyfoxserver.chat.data")
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
    			.scan("com.tvd12.ezyfoxserver.chat")
    			.build(); 
    	return bindingContext;
    }
    
    private EzyAppSetting getSetting(EzyAppContext context) {
    	return context.getApp().getSetting();
    }
    
}
