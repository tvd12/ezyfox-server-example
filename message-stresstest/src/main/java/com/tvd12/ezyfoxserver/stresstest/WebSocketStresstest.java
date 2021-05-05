/**
 * 
 */
package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyWsClient;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;

import lombok.AllArgsConstructor;

/**
 * @author tavandung12
 *
 */
@AllArgsConstructor
public class WebSocketStresstest {

	public static void main(String[] args) throws Exception {
		DefaultClientConfig clientConfig = new DefaultClientConfig();
		SocketClientSetup setup = new SocketClientSetup("websocket");
		EzyClients clients = EzyClients.getInstance();
		new Thread(() -> {
			int clientCount = 300;
			for(int i = 0 ; i < clientCount ; i++) {
				EzyWsClient client = new EzyWsClient(clientConfig.get(i));
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				setup.setup(client, false);
				clients.addClient(client);
				client.connect("ws://127.0.0.1:2208/ws");
			}
		})
		.start();
		EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
		mainEventsLoop.start(5);
	}

}
