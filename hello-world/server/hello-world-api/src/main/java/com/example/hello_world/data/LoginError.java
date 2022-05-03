package com.example.hello_world.data;

import com.tvd12.ezyfoxserver.constant.EzyILoginError;
import lombok.Getter;

@Getter
public enum LoginError implements EzyILoginError {

    PASSWORD_TOO_LONG(100, "password too long"),
    PASSWORD_TOO_SHORT(101, "password too short");

    private final int id;
    private final String message;

    private LoginError(int id, String message) {
        this.id = id;
        this.message = message;
    }

}
