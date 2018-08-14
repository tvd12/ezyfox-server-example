package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfox.util.EzyEntityBuilders;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;

/**
 * Created by tavandung12 on 6/24/17.
 */
public abstract class ChatAbstractModel extends EzyEntityBuilders implements ChatModel {

    protected EzyClientAppContext getAppContext() {
        return ChatSingleton.getInstance()
                .getClientContext()
                .getAppContext("ezyfox-chat");
    }

}
