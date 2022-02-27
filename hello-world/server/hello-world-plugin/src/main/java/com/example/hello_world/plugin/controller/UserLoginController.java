package com.example.hello_world.plugin.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

import com.example.hello_world.common.CommonConstants;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.sercurity.EzyAesCrypt;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;

@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		String accessToken = getAccessToken(event);
		if (EzyStrings.isBlank(accessToken)) {
		    loginWithUsernamePassword(event);
		} else {
		    loginWithToken(event, accessToken);
		}
	}
	
	private void loginWithUsernamePassword(EzyUserLoginEvent event) {
	    logger.info("handle user {} login in with password", event.getUsername());
	    String username = event.getUsername();
	    String password = event.getPassword();
	    if (username.length() < 6) {
	        throw new EzyLoginErrorException(EzyLoginError.INVALID_USERNAME);
	    }
	    if (password.length() < 6) {
	        throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
	    }
	    // write your code here
	}
	
	private void loginWithToken(EzyUserLoginEvent event, String token) {
	    logger.info("handle user login in with token");
	    try {
	        byte[] usernameBytes = EzyAesCrypt.getDefault().decrypt(
	            EzyBase64.decode(token),
	            CommonConstants.TOKEN_ENCRYPTION_KEY.getBytes()
            );
	        String username = new String(usernameBytes);
	        event.setUsername(username);
	    } catch (Exception e) {
	        throw new EzyLoginErrorException(EzyLoginError.INVALID_TOKEN);
        }
	}
	
	private String getAccessToken(EzyUserLoginEvent event) {
	    Object loginData = event.getData();
	    if (loginData instanceof EzyObject) {
	        return ((EzyObject) loginData).get("accessToken", String.class);
	    }
	    return null;
	}
	
}