/**
 * 
 */
package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;

import lombok.AllArgsConstructor;

/**
 * @author tavandung12
 *
 */
@AllArgsConstructor
public class TcpSocketStresstest {

	public static void main(String[] args) throws Exception {
		DefaultClientConfig clientConfig = new DefaultClientConfig();
		SocketClientSetup setup = new SocketClientSetup("websocket");
		EzyClients clients = EzyClients.getInstance();
		new Thread(() -> {
			int clientCount = 300;
			for(int i = 0 ; i < clientCount ; i++) {
				EzyTcpClient client = new EzyTcpClient(clientConfig.get(i));
				try {
					Thread.sleep(50);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				setup.setup(client);
				clients.addClient(client);
				client.connect("ws.tvd12.com", 3005);
//				client.connect("127.0.0.1", 3005);
			}
		})
		.start();
		while(true) {
			clients.processAllClientEvents();
			Thread.sleep(5);
		}
	}

}
