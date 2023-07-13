//
//  SocketProxy.swift
//  freechat-swift
//
//  Created by Dzung on 10/05/2021.
//  Copyright © 2021 Young Monkeys. All rights reserved.
//

import Foundation

public let ZONE_NAME = "example"
public let APP_NAME = "hello-world"

public class SocketProxy {
    
    private let client: EzyClient
    private static let INSTANCE = SocketProxy()
    
    private init() {
        let config = EzyClientConfig()
            .setClientName(clientName: ZONE_NAME)
            .setZoneName(zoneName: ZONE_NAME)
            .setEnableSSL()
//            .setEnableDebug()
        _ = config.ping
            .setPingPeriod(pingPeriod: 3000)
            .setMaxLostPingCount(maxLostPingCount: 3)
        _ = config.reconnect
            .setReconnectPeriod(reconnectPeriod: 1000)
            .setMaxReconnectCount(maxReconnectCount: 10)
        let clients = EzyClients.getInstance()!
        client = clients.newClient(config: config)
        let setup = client.setup!
            .addEventHandler(eventType: EzyEventType.CONNECTION_SUCCESS, handler: ExConnectionSuccessHandler())
            .addEventHandler(eventType: EzyEventType.CONNECTION_FAILURE, handler: EzyConnectionFailureHandler())
            .addEventHandler(eventType: EzyEventType.DISCONNECTION, handler: ExDisconnectionHandler())
            .addDataHandler(cmd: EzyCommand.LOGIN, handler: ExLoginSuccessHandler())
            .addDataHandler(cmd: EzyCommand.APP_ACCESS, handler: ExAppAccessHandler())
            .addDataHandler(cmd: EzyCommand.HANDSHAKE, handler: ExHandshakeHandler())
        _ = setup.setupApp(appName: APP_NAME)
        Thread.current.name = "main";
        clients.processEvents()
    }
    
    public static func getInstance() -> SocketProxy {
        return INSTANCE
    }
    
    public func connectToServer() {
        let host = "127.0.0.1"
//        let host = "ws.tvd12.com"
        client.connect(host: host, port: 3005)
    }
}

class ExConnectionSuccessHandler: EzyConnectionSuccessHandler {
}

class ExDisconnectionHandler: EzyDisconnectionHandler {
    override func postHandle(event: NSDictionary) {
        // do something here
    }
}

class ExHandshakeHandler : EzyHandshakeHandler {
    override func getLoginRequest() -> NSArray {
        let array = NSMutableArray()
        array.add(ZONE_NAME)
        array.add("swift123")
        array.add("12345678912")
        return array
    }
};

class ExLoginSuccessHandler : EzyLoginSuccessHandler {
    override func handleLoginSuccess(responseData: NSObject) {
        let array = NSMutableArray()
        array.add(APP_NAME)
        array.add(NSDictionary())
        client!.send(cmd: EzyCommand.APP_ACCESS, data: array)
    }
};

class ExAppAccessHandler : EzyAppAccessHandler {
    override func postHandle(app: EzyApp, data: NSObject) -> Void {
        let requestData = NSMutableDictionary()
        requestData["who"] = "Swift"
        app.send(cmd: "secureChat", data: requestData, encrypted: true)
    }
};
