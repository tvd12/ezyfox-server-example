package com.tvd12.ezyfoxserver.chat.client;

import javafx.application.Platform;

/**
 * Created by tavandung12 on 6/26/17.
 */
public class ChatApplication {

    public static void exit(int code) {
        Platform.exit();
        System.exit(code);
    }

}
