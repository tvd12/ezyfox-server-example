package com.tvd12.chat.fileupload.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import com.tvd12.chat.fileupload.service.ChatFileIdService;

public class ChatSimpleFileIdService implements ChatFileIdService {

    @Override
    public long generate(String originalFileName) {
        return ThreadLocalRandom.current().nextInt(10000000);
    }
    
}
