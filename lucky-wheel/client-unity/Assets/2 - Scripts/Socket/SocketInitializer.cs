using UnityEngine;
using System.Collections;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;

public class SocketInitializer : MonoBehaviour
{
    private EzyClient client;

    // Use this for initialization
    void Start()
    {
        // Enable EzyLogger
        EzyLoggerFactory.setLoggerSupply(type => new UnityLogger(type));

        // Set up socket client
        var socketProxy = SocketProxy.getInstance();
        client = socketProxy.setup();

        // Connect to socket server
        client.connect("127.0.0.1", 3005);
    }

    // Update is called once per frame
    void Update()
    {
        // Main thread pulls data from socket
        client.processEvents();
    }
}
