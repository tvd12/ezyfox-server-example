package com.tvd12.ezyfoxserver.app.response;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@EzyObjectBinding(read = false)
public class JoinGameResponse {
    private long roomId;
    private List<String> players;
}
