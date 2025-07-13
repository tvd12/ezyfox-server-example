package org.youngmonkeys.ezyfox_server_spring_example.app.request;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Data;

@Data
@EzyObjectBinding
public class GoRequest {
    private String nickName;
}
