package org.youngmonkeys.example.push_message.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.util.EzyProcessor;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezymq.kafka.EzyKafkaConsumer;
import com.tvd12.ezymq.kafka.EzyKafkaProxy;

@EzyConfigurationBefore
public class KafkaConfig implements EzyBeanConfig {

    @EzyAutoBind
    private EzyResponseFactory responseFactory;

    @Override
    public void config() {
        EzyKafkaProxy kafkaProxy = EzyKafkaProxy.builder()
            .scan("org.youngmonkeys.example.push_message.kafka")
            .ignoreUnknownComponents(true)
            .addSingleton(responseFactory)
            .build();
        EzyKafkaConsumer consumer = kafkaProxy.getConsumer("message");
        EzyProcessor.processWithException(consumer::start);
    }
}
