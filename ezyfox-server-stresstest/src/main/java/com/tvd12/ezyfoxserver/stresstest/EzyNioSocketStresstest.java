/**
 * 
 */
package com.tvd12.ezyfoxserver.stresstest;

import lombok.AllArgsConstructor;

/**
 * @author tavandung12
 *
 */
@AllArgsConstructor
public class EzyNioSocketStresstest {

	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		if(args.length > 0)
			host = args[0];
		int port = 3005;
		if(args.length > 1)
			port = Integer.parseInt(args[1].trim());
		int nsockets = 100;
		if(args.length > 2)
			nsockets = Integer.valueOf(args[2]);
		String userPrefix = "User";
		if(args.length > 3)
			userPrefix = args[3];
		boolean sendMessage = false;
		if(args.length > 4)
			sendMessage = Boolean.parseBoolean(args[4].trim());
		boolean websocket = false;
		if(args.length > 5)
			websocket = Boolean.parseBoolean(args[5].trim());
		for(int i = 0 ; i < nsockets ; i++) {
			EzyNioSocketUnit.builder()
				.host(host)
				.port(port)
				.websocket(websocket)
				.username(userPrefix + "#" + i)
				.sendMessage(sendMessage)
				.build()
				.start();
			Thread.sleep(50L);
		}
	}

}
