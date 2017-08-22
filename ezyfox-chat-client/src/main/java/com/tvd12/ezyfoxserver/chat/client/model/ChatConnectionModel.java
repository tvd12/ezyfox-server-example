package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.EzyClientStarter;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.netty.codec.MsgPackCodecCreator;
import com.tvd12.ezyfoxserver.util.EzyProcessor;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tavandung12 on 6/22/17.
 */
@Setter
@Getter
public class ChatConnectionModel implements ChatModel {

	private int port;
	private String host;
	
    @Override
    public String execute() {
    	EzyProcessor.processWithLogException(this::connectToServer);
        return SUCCESS;
    }
    
    private void connectToServer() throws Exception {
    	EzyClientStarter.builder()
			.host(host)
			.port(port)
			.fromMainThread(false)
			.clientContext(getClientContext())
			.codecCreator(new MsgPackCodecCreator())
			.build()
			.start();
    }
    
    protected EzyClientContext getClientContext() {
    	return ChatSingleton.getInstance().getClientContext();
    }
}
