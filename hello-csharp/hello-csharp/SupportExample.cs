using System;
using com.tvd12.ezyfoxserver.client.binding;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.factory;

namespace hello_csharp
{
    public class SupportExample
    {
        public void Run()
        {
            EzyBinding binding = new EzyBindingBuilder()
                .build();
            SocketProxyManager socketProxyManager = SocketProxyManager
                .getInstance();
            socketProxyManager.setDefaultZoneName("example");
            socketProxyManager.init();
            EzySocketProxy socketProxy = socketProxyManager
                .getDefaultSocketProxy()
                .setTransportType(EzyTransportType.UDP)
                .setHost("tvd12.com")
                .setLoginUsername("dungtv")
                .setLoginPassword("123456")
                .setDefaultAppName("hello-world");
            socketProxy.onLoginSuccess<Object>(HandleLoginSuccess);
            socketProxy.onLoginError<Object>(HandleLoginError);
            socketProxy.onAppAccessed<Object>(HandleAppAccessed);
            EzyAppProxy appProxy = socketProxy.getDefaultAppProxy();
            appProxy.on<Object>("udpGreet", HandleAppUdpGreet);
            socketProxy.connect();
        }

        private void HandleLoginSuccess(
            EzySocketProxy socketProxy,
            Object data
        )
        {
            Console.WriteLine("login success: " + data);
        }

        private void HandleLoginError(
            EzySocketProxy socketProxy,
            Object data
        )
        {
            Console.WriteLine("login error: " + data);
        }

        private void HandleAppAccessed(
            EzyAppProxy appProxy,
            Object data
        )
        {
            Console.WriteLine("App accessed: " + data);
            appProxy.udpSend(
                "udpGreet",
                EzyEntityFactory
                    .newObjectBuilder()
                    .append("who", "Dzung")
                    .build()
            );
        }

        private void HandleAppUdpGreet(
            EzyAppProxy appProxy,
            Object data
        )
        {
            Console.WriteLine("Upd greet: " + data);
        }
    }
}
