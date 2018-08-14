package com.tvd12.ezyfoxserver.chat.command.impl;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.chat.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.context.EzyAppContext;

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
