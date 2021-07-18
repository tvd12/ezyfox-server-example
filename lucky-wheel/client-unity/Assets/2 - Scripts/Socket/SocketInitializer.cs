using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;
using UnityEngine;

public class SocketInitializer : MonoBehaviour
{
    private EzyClient client;
    private EzyLogger logger;

    // Use this for initialization
    void Start()
    {
        // Enable EzyLogger
        EzyLoggerFactory.setLoggerSupply(type => new UnityLogger(type));
        logger = EzyLoggerFactory.getLogger<SocketInitializer>();

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
