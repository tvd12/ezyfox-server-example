/**
 * 
 */
package com.tvd12.mmorpg.stresstest.client;

import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;
import com.tvd12.ezyfoxserver.client.EzyUTClient;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;

import lombok.AllArgsConstructor;

/**
 * @author tavandung12
 *
 */
@AllArgsConstructor
public class UdpSocketStresstest {

	public static void main(String[] args) {
		Statistics.getInstance().start();
		DefaultClientConfig clientConfig = new DefaultClientConfig();
		SocketClientSetup setup = new SocketClientSetup();
		EzyClients clients = EzyClients.getInstance();
		new Thread(() -> {
			int clientCount = Statistics.NUMBER_OF_USERS;
			for(int i = 0 ; i < clientCount ; i++) {
				EzyTcpClient client = new EzyUTClient(clientConfig.get(i));
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				setup.setup(client);
				clients.addClient(client);
				client.connect("127.0.0.1", 3005);
			}
		})
		.start();
		EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
		mainEventsLoop.start(5);
	}

}
