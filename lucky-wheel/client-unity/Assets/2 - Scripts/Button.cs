using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Button : MonoBehaviour
{
	public delegate void ClickDelegate();
	public event ClickDelegate clickEvent;

	public void Click()
	{
		clickEvent?.Invoke();
		SocketRequest.getInstance().SendSpinRequest();
	}
}
