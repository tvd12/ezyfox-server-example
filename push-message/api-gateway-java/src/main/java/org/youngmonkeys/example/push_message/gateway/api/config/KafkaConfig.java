package org.youngmonkeys.example.push_message.gateway.api.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.EzySingletonFactoryAware;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezymq.kafka.EzyKafkaProxy;
import lombok.Setter;

@EzyConfigurationBefore
public class KafkaConfig implements EzySingletonFactoryAware, EzyBeanConfig {

    @Setter
    private EzySingletonFactory singletonFactory;

    @Override
    public void config() {
        EzyKafkaProxy kafkaProxy = EzyKafkaProxy.builder()
            .scan("org.youngmonkeys.example.push_message.gateway.api.kafka")
            .build();
        singletonFactory.addSingleton("messageProducer", kafkaProxy.getProducer("message"));
    }
}
