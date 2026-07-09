package com.example.hello_world.event;

import com.tvd12.ezyfoxserver.event.EzyEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserGreetEvent implements EzyEvent {
    private final String who;
}
