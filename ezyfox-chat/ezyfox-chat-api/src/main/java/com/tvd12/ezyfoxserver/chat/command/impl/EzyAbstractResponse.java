package com.tvd12.ezyfoxserver.chat.command.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfoxserver.binding.EzyMarshaller;
import com.tvd12.ezyfoxserver.chat.command.EzyChatResponse;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.util.EzyEntityBuilders;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;

@SuppressWarnings("unchecked")
public abstract class EzyAbstractResponse<T extends EzyChatResponse<T>> 
		extends EzyEntityBuilders
		implements EzyChatResponse<T> {

	protected Object data;
	protected String command;
    protected Set<EzySession> recipients = new HashSet<>();
    
    protected final EzyAppContext context;
    protected final EzyMarshaller marshaller;
    protected final EzyUserManager userManager;
    
    public EzyAbstractResponse(EzyAppContext context, EzyMarshaller marshaller) {
        this.context = context;
        this.marshaller = marshaller;
        this.userManager = context.getApp().getUserManager();
    }
    
	public T command(String command) {
        this.command = command;
        return (T) this;
    }

	@Override
    public T data(Object data) {
        this.data = data;
        return (T) this;
    }

    public T user(EzyUser user) {
        if(user != null)
            this.recipients.addAll(user.getSessions());
        return (T) this;
    }
    
    public T users(EzyUser... users) {
        return users(Arrays.asList(users));
    }

    public T users(Iterable<EzyUser> users) {
        users.forEach(this::user);
        return (T) this;
    }
    
    public T username(String username) {
        return user(userManager.getUser(username));
    }
    
    public T usernames(String... usernames) {
        Arrays.stream(usernames).forEach(this::username);
        return (T) this;
    }
    
    public T usernames(Iterable<String> usernames) {
        usernames.forEach(this::username);
        return (T) this;
    }

    @Override
    public T session(EzySession session) {
        this.recipients.add(session);
        return (T) this;
    }
    
    @Override
    public T sessions(EzySession... sessions) {
        Arrays.stream(sessions).forEach(this::session);
        return (T) this;
    }
    
    @Override
    public T sessions(Iterable<EzySession> sessions) {
        sessions.forEach(this::session);
        return (T) this;
    }
    
    public Boolean execute() {
    	response(getResponseData());
        return Boolean.TRUE;
    }
    
    protected abstract EzyData getResponseData();
    
    protected void response(EzyData data) {
        context.get(EzyAppResponse.class)
        	.command(command)
        	.params(data)
        	.sessions(recipients)
        	.execute();
    }
	
}
