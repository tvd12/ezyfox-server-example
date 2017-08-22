/**
 * 
 */
package com.tvd12.ezyfoxserver.chat;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.tvd12.ezyfoxserver.bean.EzyBeanContext;
import com.tvd12.ezyfoxserver.bean.EzySingletonFactory;
import com.tvd12.ezyfoxserver.binding.EzyBindingContext;
import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.binding.EzyUnmarshaller;
import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyEventController;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;
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
    	EzyBeanContext beanContext = EzyBeanContext.builder()
    			.addSingleton("appContext", context)
    			.addSingleton("userManager", context.getApp().getUserManager())
    			.addSingleton("marshaller", marshaller)
    			.addSingleton("unmarshaller", unmarshaller)
    			.scan("com.tvd12.ezyfoxserver.chat")
    			.build();
    	return beanContext;
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
