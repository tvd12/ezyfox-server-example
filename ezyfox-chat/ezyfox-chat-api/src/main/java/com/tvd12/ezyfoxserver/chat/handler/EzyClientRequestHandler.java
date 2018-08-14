package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfox.function.EzyHandler;
import com.tvd12.ezyfox.util.EzyEntityBuilders;
import com.tvd12.ezyfoxserver.chat.util.EzyChatAppContextAware;
import com.tvd12.ezyfoxserver.chat.util.EzyChatSessionAware;
import com.tvd12.ezyfoxserver.chat.util.EzyChatUserAware;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzyUser;

import lombok.Setter;

@Setter
public abstract class EzyClientRequestHandler 
		extends EzyEntityBuilders
		implements 
			EzyHandler,
			EzyChatAppContextAware, 
			EzyChatSessionAware, 
			EzyChatUserAware {

	protected EzyUser user;
	protected EzySession session;
	protected EzyAppContext appContext;
	
}
