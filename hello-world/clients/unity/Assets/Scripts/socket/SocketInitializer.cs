using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;
using UnityEngine;

public class SocketInitializer : MonoBehaviour
{
    static SocketInitializer instance;
    public string host = "tvd12.com";
    public int port = 3005;
    private EzyClient client;
    private EzyLogger logger;

    private void Awake()
    {
        // If go back to current scene, don't make duplication
        if (instance != null)
        {
            Destroy(gameObject);
        } else
        {
            instance = this;
            DontDestroyOnLoad(gameObject);
        }
    }

    // Use this for initialization
    void Start()
    {
        // Enable EzyLogger
        EzyLoggerFactory.setLoggerSupply(type => new UnityLogger(type));
        logger = EzyLoggerFactory.getLogger<SocketInitializer>();

        // Set up socket client
        var socketProxy = SocketProxy.getInstance();
        client = socketProxy.setup(host, port);

        // Connect to server
        socketProxy.connect();
    }

    // Update is called once per frame
    void Update()
    {
        // Main thread pulls data from socket
        client.processEvents();
    }
}