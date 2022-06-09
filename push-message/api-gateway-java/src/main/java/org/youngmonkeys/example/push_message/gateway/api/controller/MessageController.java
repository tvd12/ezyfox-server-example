package org.youngmonkeys.example.push_message.gateway.api.controller;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.RequestBody;
import com.tvd12.ezymq.kafka.EzyKafkaProducer;
import lombok.AllArgsConstructor;
import org.youngmonkeys.example.push_message.gateway.api.kafka.KafkaMessage;
import org.youngmonkeys.example.push_message.gateway.api.request.Message;

@AllArgsConstructor
@Controller("/api/v1/message")
public class MessageController {

    private final EzyKafkaProducer messageProducer;

    @DoPost("/push")
    public boolean pushMessage(@RequestBody Message message) {
        messageProducer.send(
            "push",
            new KafkaMessage(message.getUsername(), message.getData())
        );
        return Boolean.TRUE;
    }
}
