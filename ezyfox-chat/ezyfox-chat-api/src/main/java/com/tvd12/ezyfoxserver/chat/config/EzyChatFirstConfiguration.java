package com.tvd12.ezyfoxserver.chat.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;
import com.tvd12.ezyfoxserver.chat.controller.EzyChatUserRequestController;
import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfox.util.EzyLoggable;

@EzyConfiguration
public class EzyChatFirstConfiguration extends EzyLoggable implements EzyBeanConfig {

	private final EzyAppContext appContext;
	private final EzyBeanContext beanContext;

	@EzyAutoBind({"appContext", "beanContext"})
	public EzyChatFirstConfiguration(EzyAppContext appContext, EzyBeanContext beanContext) {
		this.appContext = appContext;
		this.beanContext = beanContext;
	}
	
	@Override
	public void config() {
		addUserRequestController();
	}
	
	private void addUserRequestController() {
		EzyAddEventController eventControllerAdder = appContext.get(EzyAddEventController.class);
		eventControllerAdder.add(EzyEventType.USER_REQUEST, newUserRequestController());
	}
	
	private EzyChatUserRequestController newUserRequestController() {
		return EzyChatUserRequestController.builder()
				.beanContext(beanContext).build();
	}
	
}
