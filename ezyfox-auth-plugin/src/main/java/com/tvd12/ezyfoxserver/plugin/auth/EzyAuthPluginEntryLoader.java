package com.tvd12.ezyfoxserver.plugin.auth;

import com.tvd12.ezyfoxserver.ext.EzyAbstractPluginEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;

public class EzyAuthPluginEntryLoader extends EzyAbstractPluginEntryLoader {

	@Override
	public EzyPluginEntry load() throws Exception {
		return new EzyAuthPluginEntry();
	}
	
}
