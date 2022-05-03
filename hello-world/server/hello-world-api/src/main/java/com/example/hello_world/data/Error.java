package com.example.hello_world.data;


import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class Error implements Serializable {
    private static final long serialVersionUID = -28224378850292849L;

    @EzyValue("c")
    private final int code;

    @EzyValue("m")
    private final String message;
}
