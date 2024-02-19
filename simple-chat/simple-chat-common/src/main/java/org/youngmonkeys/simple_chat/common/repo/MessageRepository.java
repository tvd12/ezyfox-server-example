package org.youngmonkeys.simple_chat.common.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.simple_chat.common.entity.Message;

@EzyRepository
public interface MessageRepository
    extends EzyDatabaseRepository<Long, Message> {}
