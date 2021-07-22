package org.youngmonkeys.example.push_message.kafka;

import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezymq.kafka.annotation.EzyKafkaHandler;
import com.tvd12.ezymq.kafka.handler.EzyKafkaMessageHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzyKafkaHandler(topic = "message", command = "push")
public class KafkaMessageHandler implements EzyKafkaMessageHandler<KafkaMessage> {

    private final EzyResponseFactory responseFactory;

    @Override
    public void process(KafkaMessage message) {
        responseFactory.newObjectResponse()
            .command("message")
            .usernames(message.getUsername())
            .data(message.getData())
            .execute();
    }
}
