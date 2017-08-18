package com.tvd12.ezyfoxserver.chat.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfoxserver.bean.EzyBeanContext;
import com.tvd12.ezyfoxserver.bean.EzyPrototypeFactory;
import com.tvd12.ezyfoxserver.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.EzyUnmarshaller;
import com.tvd12.ezyfoxserver.builder.EzyBuilder;
import com.tvd12.ezyfoxserver.chat.util.EzyChatAppContextAware;
import com.tvd12.ezyfoxserver.chat.util.EzyChatSessionAware;
import com.tvd12.ezyfoxserver.chat.util.EzyChatUserAware;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.event.EzyUserRequestAppEvent;
import com.tvd12.ezyfoxserver.function.EzyHandler;
import com.tvd12.ezyfoxserver.io.EzyMaps;
import com.tvd12.ezyfoxserver.util.EzyLoggable;

public class EzyChatUserRequestController 
		extends EzyAbstractAppEventController<EzyUserRequestAppEvent> {

	private EzyBeanContext beanContext;
	private Map<String, EzyPrototypeSupplier> handlers;
	private EzyUnmarshaller unmarshaller;
	
	@Override
	public void handle(EzyAppContext context, EzyUserRequestAppEvent event) {
		EzyArray data = event.getData();
		String cmd = data.get(0, String.class);
		EzyArray params = data.get(1, EzyArray.class);
		EzyPrototypeSupplier supplier = handlers.get(cmd);
		EzyHandler handler = (EzyHandler)supplier.supply(beanContext);
		if(handler instanceof EzyChatUserAware)
			((EzyChatUserAware)handler).setUser(event.getUser());
		if(handler instanceof EzyChatSessionAware)
			((EzyChatSessionAware)handler).setSession(event.getSession());
		if(handler instanceof EzyChatAppContextAware)
			((EzyChatAppContextAware)handler).setAppContext(context);
		setHandlerData(handler, params);
		handler.handle();
	}
	
	private void setHandlerData(Object handler, EzyData data) {
		if(handler instanceof EzyDataBinding)
			unmarshaller.unwrap(data, handler);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder
			extends EzyLoggable
			implements EzyBuilder<EzyChatUserRequestController> {
		
		private EzyBeanContext beanContext;
		private EzyPrototypeFactory prototypeFactory;
		private EzyUnmarshaller unmarshaller;
		
		public Builder beanContext(EzyBeanContext beanContext) {
			this.beanContext = beanContext;
			this.prototypeFactory = beanContext.getPrototypeFactory();
			this.unmarshaller = beanContext.getSingleton("unmarshaller", EzyUnmarshaller.class);
			return this;
		}
		
		@Override
		public EzyChatUserRequestController build() {
			EzyChatUserRequestController product = new EzyChatUserRequestController();
			product.beanContext = beanContext;
			product.unmarshaller = unmarshaller;
			product.handlers = getHandlers();
			return product;
		}
		
		@SuppressWarnings("unchecked")
		private Map<String, EzyPrototypeSupplier> getHandlers() {
			List<EzyPrototypeSupplier> suppliers = filterSuppliers();
			Map<String, EzyPrototypeSupplier> handlers = new ConcurrentHashMap<>();
			for(EzyPrototypeSupplier supplier : suppliers) {
				Map<String, String> properties = prototypeFactory.getProperties(supplier);
				String command = properties.get("cmd");
				handlers.put(command, supplier);
				getLogger().debug("add command {} and request handler supplier {}", command, supplier);
			}
			return handlers;
		}
		
		private List<EzyPrototypeSupplier> filterSuppliers() {
			return prototypeFactory.getSuppliers(
					EzyMaps.newHashMap("type", "REQUEST_HANDLER"));
		}
	}
}
