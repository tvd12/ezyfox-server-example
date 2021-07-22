using System;
using UnityEngine;

public class ClickEventButton : MonoBehaviour
{
	public event Action clickEvent;

	public void Click()
	{
		clickEvent?.Invoke();
	}
}