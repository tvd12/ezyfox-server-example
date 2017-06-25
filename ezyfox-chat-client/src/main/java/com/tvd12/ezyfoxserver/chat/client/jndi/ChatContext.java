package com.tvd12.ezyfoxserver.chat.client.jndi;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ChatContext extends InitialContext {

	private Map<String, Object> objects = new ConcurrentHashMap<>();
	
	public static final String CLIENT = "ezyfox:client";
	
	public ChatContext(Hashtable<?, ?> environment) throws NamingException {
		super(environment);
	}
	
	@Override
	public Object lookup(String name) throws NamingException {
		return objects.get(name);
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		objects.put(name, obj);
	}

}
