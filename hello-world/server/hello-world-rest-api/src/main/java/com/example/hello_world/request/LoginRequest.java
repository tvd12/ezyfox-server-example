package com.example.hello_world.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
