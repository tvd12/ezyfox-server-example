using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.logger;
using UnityEngine;
using UnityEngine.UI;

public class SpinController : MonoBehaviour
{
    public GameObject spinButton;
    public GameObject wheel;
    public Text prizeText;

    private string[] slicePrizes = new string[] {
        "A KEY!!!", "50 STARS", "500 STARS", "BAD LUCK!!!",
        "200 STARS", "100 STARS", "150 STARS", "BAD LUCK!!!" };

    private string displayText = "";

    private void Awake()
    {
        spinButton.GetComponent<Button>().clickEvent += SpinButtonClick;

        SpinResponseHandler.spinResponseEvent += SpinResponse;

        wheel.GetComponent<RotateSelf>().finishEvent += FinishSpin;
    }

    void SpinButtonClick()
    {
        if (!wheel.GetComponent<RotateSelf>().Enable)
        {
            prizeText.text = "";
            SocketRequest.getInstance().SendSpinRequest();
        }
    }

    void SpinResponse(int result)
    {
        displayText = slicePrizes[result];
        wheel.GetComponent<RotateSelf>().Activate(result);
    }

    void FinishSpin()
    {
        prizeText.text = displayText;
    }
}
