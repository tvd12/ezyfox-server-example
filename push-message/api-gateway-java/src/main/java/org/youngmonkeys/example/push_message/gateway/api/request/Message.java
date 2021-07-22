package org.youngmonkeys.example.push_message.gateway.api.request;

import lombok.Data;

import java.util.Map;

@Data
public class Message {
    private String username;
    private Map<String, Object>  data;
}
