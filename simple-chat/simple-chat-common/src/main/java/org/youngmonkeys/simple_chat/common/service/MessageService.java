package org.youngmonkeys.simple_chat.common.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.simple_chat.common.entity.Message;
import org.youngmonkeys.simple_chat.common.repo.MessageRepository;

import java.time.LocalDateTime;

@EzySingleton
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void addMessage(long userId, long channelId, String message) {
        Message entity = new Message();
        entity.setFromUserId(userId);
        entity.setToChannelId(channelId);
        entity.setMessage(message);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        messageRepository.save(entity);
    }
}
