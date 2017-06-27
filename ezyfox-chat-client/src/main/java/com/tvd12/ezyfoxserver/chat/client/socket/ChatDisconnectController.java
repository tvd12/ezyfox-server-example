package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.controller.EzyDisconnectController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientUser;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import javafx.application.Platform;

/**
 * Created by tavandung12 on 6/27/17.
 */
@SuppressWarnings("restriction")
public class ChatDisconnectController extends EzyDisconnectController {

    @Override
    public void handle(EzyClientContext ctx, EzyClientUser rev, EzyArray data) {
        super.handle(ctx, rev, data);
        Platform.runLater(this::showConnectionView);
    }

    protected void showConnectionView() {
        ChatSingleton.getInstance()
                .getViewFactory()
                .getView(ChatEventType.CONNECT, ChatModel.SUCCESS)
                .show();
    }
}
