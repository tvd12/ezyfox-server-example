using System;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.config;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.handler;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.util;

class HandshakeHandler : EzyHandshakeHandler
{
    protected override EzyRequest getLoginRequest()
    {
        return new EzyLoginRequest(
            SocketProxy.ZONE_NAME,
            "Guest",
            "123456"
        );
    }

    protected override void postHandle(EzyArray data)
    {
        logger.debug("postHandle Handsake");
    }
}

class LoginSuccessHandler : EzyLoginSuccessHandler
{
    protected override void handleLoginSuccess(EzyData responseData)
    {
        logger.debug("Log in successfully");
        SocketRequest.getInstance().SendPluginInfoRequest(SocketProxy.PLUGIN_NAME);
    }
}

class PluginInfoHandler : EzyPluginInfoHandler
{
    protected override void postHandle(EzyPlugin plugin, EzyArray data)
    {
        logger.debug("Completed setting up socket client!");
    }
}

#region Plugin Data Handler

class SpinResponseHandler : EzyAbstractPluginDataHandler<EzyObject>
{
    public static event Action<int> spinResponseEvent;

    protected override void process(EzyPlugin plugin, EzyObject data)
    {
        var result = data.get<int>("result");
        spinResponseEvent.Invoke(result);
    }
}

#endregion

public class SocketProxy : EzyLoggable
{
    private static readonly SocketProxy INSTANCE = new SocketProxy();

    public const string ZONE_NAME = "lucky-wheel";
    public const string PLUGIN_NAME = "lucky-wheel";

    private EzyClient client;

    public EzyClient Client { get => client; }

    public static SocketProxy getInstance()
    {
        return INSTANCE;
    }

    public EzyClient setup()
    {
        logger.debug("Set up socket client");
        var config = EzyClientConfig.builder()
            .clientName(ZONE_NAME)
            .build();

        var clients = EzyClients.getInstance();
        client = clients.newDefaultClient(config);
        var setup = client.setup();

        setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler());
        setup.addDataHandler(EzyCommand.LOGIN, new LoginSuccessHandler());
        setup.addDataHandler(EzyCommand.PLUGIN_INFO, new PluginInfoHandler());
        var setupPlugin = setup.setupPlugin(PLUGIN_NAME);

        setupPlugin.addDataHandler("spin", new SpinResponseHandler());
        return client;
    }
}
