package com.tvd12.ezyfoxserver.chat.component.impl;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.chat.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.chat.command.EzyObjectResponse;
import com.tvd12.ezyfoxserver.chat.command.impl.EzyArrayResponseImpl;
import com.tvd12.ezyfoxserver.chat.command.impl.EzyObjectResponseImpl;
import com.tvd12.ezyfoxserver.chat.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.context.EzyAppContext;

import lombok.Setter;

@Setter
@EzySingleton("responseFactory")
public class EzyResponseFactoryImpl implements EzyResponseFactory {

	@EzyAutoBind
	private EzyAppContext appContext;
	
	@EzyAutoBind
	private EzyMarshaller marshaller;
	
	@Override
	public EzyArrayResponse newArrayResponse() {
		return new EzyArrayResponseImpl(appContext, marshaller);
	}
	
	@Override
	public EzyObjectResponse newObjectResponse() {
		return new EzyObjectResponseImpl(appContext, marshaller);
	}
	
}
