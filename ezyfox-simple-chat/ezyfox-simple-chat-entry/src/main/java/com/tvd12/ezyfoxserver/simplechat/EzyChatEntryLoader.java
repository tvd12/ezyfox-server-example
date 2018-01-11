package com.tvd12.ezyfoxserver.simplechat;

import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.reflect.EzyClasses;

public class EzyChatEntryLoader extends EzyAbstractAppEntryLoader {

	@Override
	public EzyAppEntry load() throws Exception {
		return EzyClasses.newInstance("com.tvd12.ezyfoxserver.simplechat.EzyChatEntry");
	}
	
}
