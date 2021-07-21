using UnityEngine;
using UnityEngine.Events;
using UnityEngine.UI;

public class SpinController : MonoBehaviour
{
    public BoolVariable isWheelEnable;
    public StringVariable prizeString;
    public UnityEvent<int> spinSocketResponseEvent;

    //public Text prizeText;

    private string displayText = "";

    private void Awake()
    {
        SpinResponseHandler.spinResponseEvent += OnSpinSocketResponse;
    }

    //[SerializeField]
    public void SendSpinRequest()
    {
        if (!isWheelEnable.Value)
        {
            prizeString.SetValue("");
            SocketRequest.getInstance().SendSpinRequest();
        }
    }

    void OnSpinSocketResponse(int result)
    {
        spinSocketResponseEvent.Invoke(result);
    }
}
