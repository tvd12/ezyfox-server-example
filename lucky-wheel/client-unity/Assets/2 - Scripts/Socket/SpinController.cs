using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;
using UnityEngine;

public class SpinController : MonoBehaviour
{
    public GameObject spinButton;
    public GameObject wheel;

    private void Awake()
    {
        spinButton.GetComponent<Button>().clickEvent += SpinButtonClick;

        SpinResponseHandler.spinResponseEvent += SpinResponse;
    }

    void SpinButtonClick()
    {
        if (!wheel.GetComponent<RotateSelf>().Enable)
        {
            SocketRequest.getInstance().SendSpinRequest();
        }
    }

    void SpinResponse(int result)
    {
        wheel.GetComponent<RotateSelf>().Activate(result);
    }
}
