package com.example.hello_world.controller;

import com.example.hello_world.common.CommonConstants;
import com.example.hello_world.request.LoginRequest;
import com.example.hello_world.response.LoginResponse;
import com.tvd12.ezyfox.sercurity.EzyAesCrypt;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.RequestBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/api/v1")
public class AuthenticationController {
    
    @DoPost("/login")
    public Object loginPost(
        @RequestBody LoginRequest request
    ) throws Exception {
        String accessToken = EzyBase64.encode2utf(
            EzyAesCrypt.getDefault().encrypt(
                request.getUsername().getBytes(),
                CommonConstants.TOKEN_ENCRYPTION_KEY.getBytes()
            )
        );
        return new LoginResponse(accessToken);
    }
}
