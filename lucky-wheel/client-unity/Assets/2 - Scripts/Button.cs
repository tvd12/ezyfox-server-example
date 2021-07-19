using System;
using UnityEngine;

public class Button : MonoBehaviour
{
	public event Action clickEvent;

	public void Click()
	{
		clickEvent?.Invoke();
	}
}
