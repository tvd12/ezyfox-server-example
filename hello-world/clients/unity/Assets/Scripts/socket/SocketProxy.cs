using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.config;
using com.tvd12.ezyfoxserver.client.evt;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.handler;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.util;
using UnityEngine;

class DisconnectionHandler : EzyDisconnectionHandler
{
    protected override void postHandle(EzyDisconnectionEvent evt)
    {
        logger.info("event = " + evt);
    }
}

class HandshakeHandler : EzyHandshakeHandler
{
    protected override EzyRequest getLoginRequest()
    {
        return new EzyLoginRequest("example", "username", "passworld");
    }
}

public class SocketProxy : EzyLoggable
{
    private static readonly SocketProxy INSTANCE = new SocketProxy();

    public const string ZONE_NAME = "hello-world";
    public const string PLUGIN_NAME = "hello-world";

    private EzyClient client;
    private string host;
    private int port;

    public EzyClient Client { get => client; }
    public string Host { get => host; }
    public int Port { get => port; }

    public static SocketProxy getInstance()
    {
        return INSTANCE;
    }

    public EzyClient setup(string host, int port)
    {
        this.host = host;
        this.port = port;

        logger.debug("Set up socket client");
        var config = EzyClientConfig.builder()
            .clientName(ZONE_NAME)
            .pingConfigBuilder()
                .pingPeriod(1000)
                .maxLostPingCount(5)
                .done()
            .build();

        var clients = EzyClients.getInstance();
        client = clients.newDefaultClient(config);
        var setup = client.setup();
        setup.addEventHandler(EzyEventType.DISCONNECTION, new DisconnectionHandler());
        setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler());

        return client;
    }

    public void connect()
    {
        client.connect("tvd12.com", 3005);
    }

}