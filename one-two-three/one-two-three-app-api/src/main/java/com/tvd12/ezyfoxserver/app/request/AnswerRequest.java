package com.tvd12.ezyfoxserver.app.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfoxserver.app.data.GameAnswer;
import lombok.Data;

@Data
@EzyObjectBinding(write = false)
public class AnswerRequest {
    private GameAnswer answer;
}
