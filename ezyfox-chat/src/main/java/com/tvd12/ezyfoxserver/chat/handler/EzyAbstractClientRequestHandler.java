package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.factory.EzyFactory;
import com.tvd12.ezyfoxserver.util.EzyLoggable;

public abstract class EzyAbstractClientRequestHandler
		extends EzyLoggable
		implements EzyClientRequestHandler {

	protected EzyArrayBuilder newArrayBuilder() {
		return EzyFactory.create(EzyArrayBuilder.class);
	}
	
}
