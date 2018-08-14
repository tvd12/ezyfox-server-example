package com.tvd12.ezyfoxserver.ex.videopoker;

import static com.tvd12.ezyfoxserver.util.EzyAutoImplAnnotations.getBeanName;
import static com.tvd12.ezyfoxserver.util.EzyMapBuilder.mapBuilder;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.hazelcast.core.HazelcastInstance;
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
import com.tvd12.ezyfoxserver.context.EzyServerContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ex.videopoker.config.AppConfig;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;
import com.tvd12.ezyfoxserver.function.EzyApply;
import com.tvd12.ezyfoxserver.hazelcast.factory.EzyMapTransactionFactory;
import com.tvd12.ezyfoxserver.morphia.EzyDataStoreBuilder;
import com.tvd12.ezyfoxserver.morphia.bean.EzyMorphiaRepositories;
import com.tvd12.ezyfoxserver.setting.EzyAppSetting;
import com.tvd12.properties.file.mapping.PropertiesMapper;
import com.tvd12.properties.file.reader.BaseFileReader;

public class AppEntry extends EzyAbstractAppEntry {

	@Override
	public void config(EzyAppContext ctx) {
		getLogger().info("\n=================== VIDEOPOKER APP START CONFIG ================\n");
		EzyAppSetting setting = getSetting(ctx);
		String appConfigFile = setting.getConfigFile();
		AppConfig appConfig = readAppConfig(appConfigFile);
		String databaseName = appConfig.getDatabaseName(); 
		
		EzyServerContext globalContext = ctx.getParent();
		MongoClient mongoClient = globalContext.get(MongoClient.class);
		HazelcastInstance hzInstance = globalContext.get(HazelcastInstance.class);
		EzyMapTransactionFactory mapTransactionFactory = globalContext.get(EzyMapTransactionFactory.class);
		
		Datastore datastore = newDatastore(mongoClient, databaseName);
		
		EzyBeanContext beanContext = createBeanContext(ctx, builder ->  {
			builder.addSingleton("mongoClient", mongoClient);
			builder.addSingleton("datastore", datastore);
			builder.addSingleton("hzInstance", hzInstance);
			builder.addSingleton("hazelcastInstance", hzInstance);
			builder.addSingleton("mapTransactionFactory", mapTransactionFactory);
			addAutoImplMongoRepo(builder, datastore);
		});
		
		addEventControllers(ctx, beanContext);
		
		getLogger().info("\n=================== FREE CHAT APP END CONFIG ================\n");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addEventControllers(EzyAppContext appContext, EzyBeanContext beanContext) {
		EzySingletonFactory singletonFactory = beanContext.getSingletonFactory();
		EzyAddEventController eventControllerAdder = appContext.get(EzyAddEventController.class);
		
		List<Object> eventControllers = beanContext
				.getSingletons(mapBuilder().put("type", "EVENT_HANDLER").build());
		for (Object controller : eventControllers) {
			Map<String, String> prop = singletonFactory.getProperties(controller);
			EzyEventType eventType = EzyEventType.valueOf(prop.get("name"));
			eventControllerAdder.add(eventType, (EzyEventController) controller);
			getLogger().info("add  event {} controller {}", eventType, controller);
		}
	}

	@Override
	public void start() throws Exception {
		getLogger().info("start free chat app");
	}

	@Override
	public void destroy() {
		getLogger().info("destroy free chat app");
	}

	private EzyBeanContext createBeanContext(
			EzyAppContext context, EzyApply<EzyBeanContextBuilder> applier) {
		EzyBindingContext bindingContext = createBindingContext();
		EzyMarshaller marshaller = bindingContext.newMarshaller();
		EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
		EzyBeanContextBuilder beanContextBuilder = EzyBeanContext.builder()
				.addSingleton("appContext", context)
				.addSingleton("userManager", context.getApp().getUserManager())
				.addSingleton("marshaller", marshaller)
				.addSingleton("unmarshaller", unmarshaller)
				.scan("com.tvd12.ezyfox.support.factory")
				.scan("com.tvd12.ezyfox.ex.videopoker.repo")
				.scan("com.tvd12.ezyfox.ex.videopoker.controller")
				.scan("com.tvd12.ezyfox.ex.videopoker.handler")
				.scan("com.tvd12.ezyfox.ex.videopoker.config")
				.scan("com.tvd12.ezyfox.ex.videopoker.component")
				.scan("com.tvd12.ezyfox.ex.videopoker.impl")
				.scan("com.tvd12.ezyfox.ex.videopoker.service");
		

		applier.apply(beanContextBuilder);

		return beanContextBuilder.build();

	}
	
	private void addAutoImplMongoRepo(EzyBeanContextBuilder builder, Datastore datastore) {
		Map<Class<?>, Object> additionalRepo = implementMongoRepo(datastore);
		for (Class<?> repoType : additionalRepo.keySet()) {
			builder.addSingleton(getBeanName(repoType), additionalRepo.get(repoType));
		}
	}

	private EzyBindingContext createBindingContext() {
		EzyBindingContext bindingContext = EzyBindingContext.builder()
				.scan("com.tvd12.ezyfox.ex.videopoker")
				.build();
		return bindingContext;
	}

	private Map<Class<?>, Object> implementMongoRepo(Datastore datastore) {
		return EzyMorphiaRepositories.newRepositoriesImplementer()
				.scan("com.tvd12.ezyfox.ex.videopoker.repo")
				.implement(datastore);
	}

	private Datastore newDatastore(MongoClient mongoClient, String databaseName) {
		return EzyDataStoreBuilder.dataStoreBuilder()
				.mongoClient(mongoClient).databaseName(databaseName)
				.scan("com.tvd12.ezyfox.ex.videopoker.data").build();
	}
	
	private AppConfig readAppConfig(String appConfigFile) {
		return new PropertiesMapper()
				.file(appConfigFile)
				.context(getClass())
				.clazz(AppConfig.class)
				.reader(new BaseFileReader())
				.map();
	}
	
	private EzyAppSetting getSetting(EzyAppContext context) {
		return context.getApp().getSetting();
	}

}
