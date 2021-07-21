using UnityEngine;
using UnityEngine.UI;

public class SpinController : MonoBehaviour
{
    public ClickEventButton spinButton;
    public GameObject wheel;
    public Text prizeText;

    private string[] slicePrizes = new string[] {
        "A KEY!!!", "50 STARS", "500 STARS", "BAD LUCK!!!",
        "200 STARS", "100 STARS", "150 STARS", "BAD LUCK!!!" };

    private string displayText = "";

    private void Awake()
    {
        spinButton.clickEvent += OnSpinButtonClick;

        SpinResponseHandler.spinResponseEvent += OnSpinSocketResponse;

        wheel.GetComponent<RotateSelf>().finishEvent += OnWheelFinishSpin;
    }

    void OnSpinButtonClick()
    {
        if (!wheel.GetComponent<RotateSelf>().Enable)
        {
            prizeText.text = "";
            SocketRequest.getInstance().SendSpinRequest();
        }
    }

    void OnSpinSocketResponse(int result)
    {
        displayText = slicePrizes[result];
        wheel.GetComponent<RotateSelf>().Activate(result);
    }

    void OnWheelFinishSpin()
    {
        prizeText.text = displayText;
    }
}
