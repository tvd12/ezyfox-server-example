package com.tvd12.ezyfoxserver.chat.client.constant;

import lombok.Getter;

/**
 * Created by tavandung12 on 6/22/17.
 */
public enum ChatEventType {

    START("start"),
    CONNECT("connectButton"),
    LOGIN("loginButton"),
    CHAT("chatButton"),
	SEARCH("searchButton");

    @Getter
    private String nodeId;

    private ChatEventType(String nodeId) {
        this.nodeId = nodeId;
    }

    public static ChatEventType valueOfNodeId(String nodeId) {
        for(ChatEventType type : values())
            if(nodeId.equals(type.getNodeId()))
                return type;
        throw new IllegalArgumentException("has not type with nodeId = " + nodeId);
    }

}
