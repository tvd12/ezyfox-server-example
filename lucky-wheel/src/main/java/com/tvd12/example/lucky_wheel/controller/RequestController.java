package com.tvd12.example.lucky_wheel.controller;

import com.tvd12.example.lucky_wheel.service.WheelService;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import lombok.AllArgsConstructor;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

@AllArgsConstructor
@EzyRequestController
public class RequestController extends EzyLoggable {
	private final WheelService wheelService;
	private final EzyResponseFactory responseFactory;
	
	@EzyDoHandle("spin")
	public void spin(EzySession session) {
		int result = wheelService.spin();
		responseFactory.newObjectResponse()
				.command("spin")
				.param("result", result)
				.session(session)
				.execute();
	}
}
