package com.tvd12.greeting.app.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class GoRequest {
    private String nickName;
}
