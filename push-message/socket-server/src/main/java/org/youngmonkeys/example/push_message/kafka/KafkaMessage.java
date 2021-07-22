package org.youngmonkeys.example.push_message.kafka;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@EzyMessage
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private String username;
    private EzyObject data;
}
