package com.example.hello_world.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class ChatResponse {
    private final String message;
}
