package org.youngmonkeys.example.push_message.gateway.api.request;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.Data;

@Data
public class Message {
    private String username;
    private String title;
    private String content;

    public EzyObject getDataObject() {
        return EzyEntityFactory.newObjectBuilder()
            .append("title", title)
            .append("content", content)
            .build();
    }
}
