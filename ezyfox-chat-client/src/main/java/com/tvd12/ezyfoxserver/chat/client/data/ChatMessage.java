package com.tvd12.ezyfoxserver.chat.client.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by tavandung12 on 6/22/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String sender;
    private String message;
    private String receiver;
    private Date receiveDate = new Date();

    @Override
    public String toString() {
        return new StringBuilder()
                .append(sender)
                .append(" : ")
                .append(message)
                .toString();
    }
}
