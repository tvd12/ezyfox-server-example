package com.tvd12.ezyfoxserver.plugin.auth.command.impl;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.entity.EzyObject;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyObjectResponse;

public class EzyObjectResponseImpl 
		extends EzyAbstractResponse<EzyObjectResponse>
		implements EzyObjectResponse {

	protected final Map<Object, Object> additionalParams = new HashMap<>();
	
	public EzyObjectResponseImpl(EzyPluginContext context, EzyMarshaller marshaller) {
		super(context, marshaller);
	}
	
	@Override
	public EzyObjectResponse param(Object key, Object value) {
		additionalParams.put(key, value);
		return this;
	}

	@Override
	protected EzyData getResponseData() {
		EzyObject object = data != null
				? marshaller.marshal(data)
				: newObjectBuilder().build();
		object.putAll(additionalParams);
		return object;
	}
	
}
