/**
 * 
 */
package com.tvd12.ezyfoxserver.plugin.auth;

import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntry;
import com.tvd12.ezyfoxserver.plugin.auth.controller.EzyServerReadyController;
import com.tvd12.ezyfoxserver.plugin.auth.controller.EzyUserLoginController;

/**
 * @author tavandung12
 *
 */
public class EzyAuthPluginEntry extends EzyAbstractPluginEntry {

	@Override
	public void config(EzyPluginContext ctx) {
		getLogger().info("auth plugin: config");
		ctx.get(EzyAddEventController.class).add(
				EzyEventType.SERVER_READY, new EzyServerReadyController());
		ctx.get(EzyAddEventController.class).add(
				EzyEventType.USER_LOGIN, new EzyUserLoginController());
	}
	
	@Override
	public void start() throws Exception {
		getLogger().info("auth plugin: start");
	}

	@Override
	public void destroy() {
		getLogger().info("auth plugin: destry");
	}

}
