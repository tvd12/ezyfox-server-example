using UnityEngine;
using System.Collections;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;
using com.tvd12.ezyfoxserver.client.util;

public class SocketController : MonoBehaviour
{
    private EzyClient client;
    private EzyLogger logger;

    public GameObject spinButton;
    public GameObject wheel;

    private void Awake()
    {
        spinButton.GetComponent<Button>().clickEvent += SpinButtonClick;

        SpinResponseHandler.spinResponseEvent += SpinResponse;
    }

    // Use this for initialization
    void Start()
    {
        // Enable EzyLogger
        EzyLoggerFactory.setLoggerSupply(type => new UnityLogger(type));
        logger = EzyLoggerFactory.getLogger<SocketController>();

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

    #region Listen to UI event

    void SpinButtonClick()
    {
        if (!wheel.GetComponent<RotateSelf>().Enable)
        {
            SocketRequest.getInstance().SendSpinRequest();
        }
    }

    #endregion

    #region Listen to Socket event

    void SpinResponse(int result)
    {
        wheel.GetComponent<RotateSelf>().Activate(result);
    }

    #endregion

}
