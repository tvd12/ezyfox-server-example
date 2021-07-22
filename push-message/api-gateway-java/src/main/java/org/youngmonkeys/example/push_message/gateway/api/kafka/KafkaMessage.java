package org.youngmonkeys.example.push_message.gateway.api.kafka;

import com.tvd12.ezyfox.message.annotation.EzyMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@EzyMessage
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private String username;
    private Map<String, Object> data;
}
