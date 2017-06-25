package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.factory.EzyFactory;

/**
 * Created by tavandung12 on 6/24/17.
 */
public abstract class ChatAbstractModel implements ChatModel {

    protected EzyArrayBuilder newArrayBuilder() {
        return EzyFactory.create(EzyArrayBuilder.class);
    }

    protected EzyObjectBuilder newObjectBuilder() {
        return EzyFactory.create(EzyObjectBuilder.class);
    }

    protected EzyClientAppContext getAppContext() {
        return ChatSingleton.getInstance()
                .getClientContext()
                .getAppContext("ezyfox-chat");
    }

}
