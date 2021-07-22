using UnityEngine;
using UnityEngine.Events;

public class SpinController : MonoBehaviour
{
    public BoolVariable isWheelEnable;
    public StringVariable prizeString;
    public UnityEvent<int> spinSocketResponseEvent;

    private void Awake()
    {
        SpinResponseHandler.spinResponseEvent += OnSpinSocketResponse;
    }

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
