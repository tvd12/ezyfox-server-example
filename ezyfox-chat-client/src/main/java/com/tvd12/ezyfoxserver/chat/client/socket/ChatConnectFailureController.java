package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatConnectionView;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;
import com.tvd12.ezyfoxserver.client.controller.EzyConnectFailureController;
import com.tvd12.ezyfoxserver.constant.EzyConstant;
import javafx.application.Platform;

/**
 * Created by tavandung12 on 6/30/17.
 */
public class ChatConnectFailureController implements EzyConnectFailureController {

    @Override
    public void handle(EzyConstant error) {
        Platform.runLater(() -> updateView(error));
    }

    protected void updateView(EzyConstant error) {
        getView().update(EzyClientCommand.CONNECT_FAILURE, error);
    }

    protected ChatConnectionView getView() {
        return ChatSingleton.getInstance()
                .getViewFactory()
                .getView(ChatEventType.CONNECT, ChatModel.SUCCESS);
    }
}
