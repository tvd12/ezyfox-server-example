package com.tvd12.ezyfoxserver.stresstest;

import static com.tvd12.ezyfoxserver.client.setting.EzySocketSettingBuilder.socketSettingBuilder;
import static com.tvd12.ezyfoxserver.client.setting.EzyWebSocketSettingBuilder.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfoxserver.builder.EzyBuilder;
import com.tvd12.ezyfoxserver.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.cmd.EzyEnableSocket;
import com.tvd12.ezyfoxserver.client.cmd.EzyEnableWebSocket;
import com.tvd12.ezyfoxserver.client.cmd.EzySendRequest;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.context.EzyClientContextBuilder;
import com.tvd12.ezyfoxserver.client.controller.EzyClientAppController;
import com.tvd12.ezyfoxserver.client.controller.EzyHandShakeController;
import com.tvd12.ezyfoxserver.client.controller.EzyLoginController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientSession;
import com.tvd12.ezyfoxserver.client.entity.EzySimpleClientUser;
import com.tvd12.ezyfoxserver.client.listener.EzyClientAppResponseListener;
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.serialize.EzyRequestSerializer;
import com.tvd12.ezyfoxserver.client.serialize.impl.EzyRequestSerializerImpl;
import com.tvd12.ezyfoxserver.client.setting.EzySocketSettingBuilder;
import com.tvd12.ezyfoxserver.client.setting.EzyWebSocketSettingBuilder;
import com.tvd12.ezyfoxserver.concurrent.EzyExecutors;
import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.entity.EzyObject;
import com.tvd12.ezyfoxserver.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.util.EzyLoggable;
import com.tvd12.ezyfoxserver.util.EzyStartable;

public class EzyNioSocketUnit extends EzyLoggable implements EzyStartable {

	private String host;
	private int port;
	private String username;
	private boolean websocket;
	private boolean sendMessage;
	
	private EzyClient client;
	private ScheduledExecutorService sendMessageService;
	
	private static final AtomicLong MESSAGE_COUNT = new AtomicLong(0);
	
	public EzyNioSocketUnit(Builder builder) {
		this.host = builder.host;
		this.port = builder.port;
		this.username = builder.username;
		this.websocket = builder.websocket;
		this.sendMessage = builder.sendMessage;
		this.client = new EzyClient();
		this.sendMessageService = EzyExecutors.newSingleThreadScheduledExecutor("client-send-message");
	}
	
	public void start() throws Exception {
		this.setupClient();
		this.start0();
	}
	
	private void start0() throws Exception {
		EzyClientContext context = newClientContext(client);
		if(websocket) {
			EzyWebSocketSettingBuilder socketSettingBuilder = webSocketSettingBuilder()
					.uri("ws://" + host + ":" + port + "/ws");
			context.get(EzyEnableWebSocket.class)
				.socketSetting(socketSettingBuilder)
				.execute();
		}
		else {
			EzySocketSettingBuilder socketSettingBuilder = socketSettingBuilder()
					.serverHost(host)
					.serverPort(port);
			context.get(EzyEnableSocket.class)
				.socketSetting(socketSettingBuilder)
				.execute();
		}
	}

	protected EzyRequestSerializer newRequestSerializer() {
		return EzyRequestSerializerImpl.builder().build();
	}

	private EzyClientContext newClientContext(EzyClient client) {
		return new EzyClientContextBuilder()
				.client(client)
				.build();
	}

	private void setupClient() {

		client.addController(EzyClientCommand.HANDSHAKE, new EzyHandShakeController() {
			@Override
			public void handle(EzyClientContext ctx, EzyClientSession session, EzyArray data) {
				super.handle(ctx, session, data);
				sendLoginRequest(ctx, session);
			}

			protected void sendLoginRequest(EzyContext ctx, EzyClientSession session) {
				ctx.get(EzySendRequest.class)
					.sender(session)
					.request(newLoginRequest())
					.execute();
			}

			protected String getUsername() {
				return username;
			}

			protected String getPassword() {
				return "123456";
			}

			protected EzyLoginRequest newLoginRequest() {
				return EzyLoginRequest.builder().username(getUsername()).password(getPassword()).data(newLoginInData())
						.build();
			}

			protected EzyData newLoginInData() {
				return newArrayBuilder().append("1.0.0").append("android").build();
			}
		});

		client.addController(EzyClientCommand.LOGIN, new EzyLoginController() {

			@Override
			protected void processNotReconnect(EzyClientContext ctx, EzyClientSession session, EzyArray data) {
				if(sendMessage)
					sendAccessAppsRequest(ctx);
			}

			protected void sendAccessAppsRequest(EzyClientContext ctx) {
				ctx.get(EzySendRequest.class)
					.sender(ctx.getMe())
					.request(newAccessAppRequest())
					.execute();
			}

			protected EzyAccessAppRequest newAccessAppRequest() {
				return EzyAccessAppRequest.builder()
						.appName("ezyfox-simple-chat")
						.data(newAccessAppData())
						.build();
			}

			protected EzyObject newAccessAppData() {
				return newObjectBuilder().build();
			}

		});

		client.addAppController(EzyClientCommand.ACESS_APP_SUCCESS, new EzyClientAppController<EzyArray>() {

			@Override
			public void handle(EzyClientAppContext ctx, EzySimpleClientUser rev, EzyArray data) {
//				sendChatMessageRequest(ctx);
//				sendPluginRequest(ctx);
				sendMessageService.scheduleAtFixedRate(() -> sendChatMessageRequest(ctx), 5, 5, TimeUnit.SECONDS);
			}

			protected void sendChatMessageRequest(EzyClientAppContext appCtx) {
				String message = "Message#" + MESSAGE_COUNT.incrementAndGet();
				EzyObject params = EzyEntityFactory.create(EzyObjectBuilder.class)
						.append("message", message)
						.build();
				appCtx.sendRequest("smsg", params);
				String username = appCtx.getMe().getName();
				getLogger().debug(username + " send message: " + message);
			}

//			protected void sendPluginRequest(EzyClientAppContext ctx) {
//				ctx.sendPluginRequest("ezyfox-auth-plugin", newPluginRequestData());
//			}
//
//			protected EzyArray newPluginRequestData() {
//				return EzyEntityFactory.create(EzyArrayBuilder.class).append(1).append("hello world").build();
//			}

		});

		client.addClientAppResponseListener("smsg", new EzyClientAppResponseListener<EzyObject>() {

			@Override
			public void execute(EzyClientAppContext ctx, EzyObject params) {
//				System.out.println("\n\nreceived message = " + params + "\n\n");
			}
		});
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyNioSocketUnit> {
		
		private String host;
		private int port;
		private String username;
		private boolean websocket;
		private boolean sendMessage;
		
		public Builder host(String host) {
			this.host = host;
			return this;
		}
		
		public Builder port(int port) {
			this.port = port;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder websocket(boolean websocket) {
			this.websocket = websocket;
			return this;
		}
		
		public Builder sendMessage(boolean sendMessage) {
			this.sendMessage = sendMessage;
			return this;
		}
		
		@Override
		public EzyNioSocketUnit build() {
			return new EzyNioSocketUnit(this);
		}
		
	}
	
}
