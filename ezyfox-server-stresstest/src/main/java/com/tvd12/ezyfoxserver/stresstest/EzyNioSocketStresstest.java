/**
 * 
 */
package com.tvd12.ezyfoxserver.stresstest;

import static com.tvd12.ezyfoxserver.client.setting.EzySocketSettingBuilder.socketSettingBuilder;

import com.tvd12.ezyfoxserver.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.cmd.EzyEnableSocket;
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
import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.entity.EzyObject;
import com.tvd12.ezyfoxserver.factory.EzyEntityFactory;

import lombok.AllArgsConstructor;

/**
 * @author tavandung12
 *
 */
@AllArgsConstructor
public class EzyNioSocketStresstest {

	public static void main(String[] args) throws Exception {
		EzyNioSocketStresstest stresstest = new EzyNioSocketStresstest();
		if(args.length < 2)
			System.err.println(stresstest.getErrorMessage());
		int nsockets = 100;
		if(args.length > 2)
			nsockets = Integer.valueOf(args[2]);
		String userPrefix = "User";
		if(args.length > 3)
			userPrefix = args[3];
		for(int i = 0 ; i < nsockets ; i++) {
			stresstest.start(args[0], Integer.parseInt(args[1]), userPrefix + "#" + i);
			Thread.sleep(50L);
		}
	}

	private void start(String host, int port, String username) throws Exception {
		EzyClient client = new EzyClient();
		setupClient(client, username);
		EzySocketSettingBuilder socketSettingBuilder = socketSettingBuilder()
				.serverHost(host)
				.serverPort(port);
		EzyClientContext context = newClientContext(client);
		context.get(EzyEnableSocket.class)
			.socketSetting(socketSettingBuilder)
			.execute();
	}

	protected EzyRequestSerializer newRequestSerializer() {
		return EzyRequestSerializerImpl.builder().build();
	}

	private EzyClientContext newClientContext(EzyClient client) {
		return new EzyClientContextBuilder()
				.client(client)
				.build();
	}

	private void setupClient(EzyClient client, String username) {

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
//				sendAccessAppsRequest(ctx);
			}

			@SuppressWarnings("unused")
			protected void sendAccessAppsRequest(EzyClientContext ctx) {
				ctx.get(EzySendRequest.class)
					.sender(ctx.getMe())
					.request(newAccessAppRequest())
					.execute();
			}

			protected EzyAccessAppRequest newAccessAppRequest() {
				return EzyAccessAppRequest.builder()
						.appName("ezyfox-chat")
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
				sendChatMessageRequest(ctx);
				sendPluginRequest(ctx);				
			}

			protected void sendChatMessageRequest(EzyClientAppContext appCtx) {
				EzyArray params = EzyEntityFactory.create(EzyArrayBuilder.class).append("nice to meet you!").build();
				for (int i = 0; i < 1000; i++)
					appCtx.sendRequest("1", params);
			}

			protected void sendPluginRequest(EzyClientAppContext ctx) {
				ctx.sendPluginRequest("ezyfox-auth-plugin", newPluginRequestData());
			}

			protected EzyArray newPluginRequestData() {
				return EzyEntityFactory.create(EzyArrayBuilder.class).append(1).append("hello world").build();
			}

		});

		client.addClientAppResponseListener("1", new EzyClientAppResponseListener<EzyArray>() {

			@Override
			public void execute(EzyClientAppContext ctx, EzyArray params) {
				System.out.println("\n\nparams = " + params + "\n\n");
			}
		});
	}

	private String getErrorMessage() {
		return "Usage: " + getClass() + " <host> <port>";
	}

}
