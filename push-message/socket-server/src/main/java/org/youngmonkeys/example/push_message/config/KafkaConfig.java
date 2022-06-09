package org.youngmonkeys.example.push_message.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezymq.kafka.EzyKafkaProxy;
import lombok.Setter;

@Setter
@EzyConfigurationAfter
public class KafkaConfig implements EzyBeanConfig {

    @EzyAutoBind
    private EzyResponseFactory responseFactory;

    @Override
    public void config() {
        EzyKafkaProxy.builder()
            .scan("org.youngmonkeys.example.push_message.kafka")
            .addSingleton(responseFactory)
            .build();
    }
}
