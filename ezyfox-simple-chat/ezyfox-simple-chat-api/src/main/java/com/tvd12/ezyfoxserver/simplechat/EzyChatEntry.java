/**
 * 
 */
package com.tvd12.ezyfoxserver.simplechat;

import com.tvd12.ezyfoxserver.command.EzyAddEventController;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntry;
import com.tvd12.ezyfoxserver.simplechat.controller.EzyChatServerReadyController;
import com.tvd12.ezyfoxserver.simplechat.controller.EzyChatUserRequestController;

/**
 * @author tavandung12
 *
 */
public class EzyChatEntry extends EzyAbstractAppEntry {

	@Override
	public void config(EzyAppContext ctx) {
		getLogger().info("simple chat app config");
		EzyAddEventController eventControllerAdder = ctx.get(EzyAddEventController.class);
		eventControllerAdder.add(EzyEventType.SERVER_READY, new EzyChatServerReadyController());
		eventControllerAdder.add(EzyEventType.USER_REQUEST, new EzyChatUserRequestController());
	}
	
    @Override
    public void start() throws Exception {
    		getLogger().info("simple chat app start");
    }
    
    @Override
    public void destroy() {
    		getLogger().info("simple chat app destroy");
    }
    
}
