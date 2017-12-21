package com.tvd12.ezyfoxserver.chat.client.model;

import static com.tvd12.ezyfoxserver.client.setting.EzySocketSettingBuilder.socketSettingBuilder;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.cmd.EzyEnableSocket;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.setting.EzySocketSettingBuilder;
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
    		EzySocketSettingBuilder socketSettingBuilder = socketSettingBuilder()
    				.serverHost(host)
    				.serverPort(port);
    		EzyClientContext context = getClientContext();
    		context.get(EzyEnableSocket.class)
    			.socketSetting(socketSettingBuilder)
    			.execute();
    }
    
    protected EzyClientContext getClientContext() {
    		return ChatSingleton.getInstance().getClientContext();
    }
}
