using System;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.config;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.evt;
using com.tvd12.ezyfoxserver.client.factory;
using com.tvd12.ezyfoxserver.client.handler;
using com.tvd12.ezyfoxserver.client.logger;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.util;
using UnityEngine;

public class SocketRequest : EzyLoggable
{
    private static SocketRequest _instance;

    public static SocketRequest getInstance()
    {
        if (_instance == null)
        {
            _instance = new SocketRequest();
        }

        return _instance;
    }


    public void SendSpinRequest()
    {
        var client = SocketProxy.getInstance().Client;
        var plugin = client.getPlugin();
        plugin?.send("spin");
    }

    public void sendPluginInfoRequest(string pluginName)
    {
        var client = SocketProxy.getInstance().Client;

        var request = EzyEntityFactory.newArrayBuilder()
            .append(pluginName)
            .build();

        client.send(EzyCommand.PLUGIN_INFO, request);
    }
}
