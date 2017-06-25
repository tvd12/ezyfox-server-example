package com.tvd12.ezyfoxserver.chat.client.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class ChatContextFactory implements InitialContextFactory {

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		return new ChatContext(environment);
	}

}
