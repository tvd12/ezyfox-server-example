/**
 * 
 */
package com.tvd12.ezyfoxserver.chat;

import com.tvd12.ezyfoxserver.chat.controller.EzyServerReadyController;
import com.tvd12.ezyfoxserver.chat.controller.EzyUserRequestController;
import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;

/**
 * @author tavandung12
 *
 */
public class EzyChatEntry extends EzyAbstractAppEntry {

	@Override
	public void config(EzyAppContext ctx) {
		getLogger().info("Chat app config");
		ctx.get(EzyAddEventController.class)
			.add(EzyEventType.SERVER_READY, new EzyServerReadyController());
		ctx.get(EzyAddEventController.class)
			.add(EzyEventType.USER_REQUEST, new EzyUserRequestController());
	}
	
    @Override
    public void start() throws Exception {
    	getLogger().info("Chat app start");
    }
    
    @Override
    public void destroy() {
    	getLogger().info("Chat app destroy");
    }
    
}
