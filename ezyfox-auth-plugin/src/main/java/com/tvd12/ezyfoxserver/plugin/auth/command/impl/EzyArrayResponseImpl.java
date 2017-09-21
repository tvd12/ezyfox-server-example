package com.tvd12.ezyfoxserver.plugin.auth.command.impl;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.plugin.auth.command.impl.EzyAbstractResponse;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyData;

public class EzyArrayResponseImpl 
		extends EzyAbstractResponse<EzyArrayResponse>
		implements EzyArrayResponse {
	
	protected final List<Object> additionalParams = new ArrayList<>();

	public EzyArrayResponseImpl(EzyAppContext context, EzyMarshaller marshaller) {
		super(context, marshaller);
	}

	@Override
	public EzyArrayResponse param(Object value) {
		additionalParams.add(value);
		return this;
	}
	
	@Override
	protected EzyData getResponseData() {
		EzyArray array = data != null 
				? marshaller.marshal(data) 
				: newArrayBuilder().build();
		array.add(additionalParams);
		return array;
	}
	
}
