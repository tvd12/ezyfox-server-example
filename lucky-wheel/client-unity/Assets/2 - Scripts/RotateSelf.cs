using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RotateSelf : MonoBehaviour
{
	private const float MIN_SPEED = 10.0f;
	private const float MAX_SPEED = 800.0f;
	private float currentAngle = 0.0f;
	private float totalAngle;

	private float slices = 8;
	private float speed;

	private bool enable = false;

    public bool Enable { get => enable; }

	public delegate void FinishDelegate();
	public event FinishDelegate finishEvent;

	// Start is called before the first frame update
	void Start()
    {
    	Deactivate();
    }

    void Deactivate() 
    {
        enable = false;
	}

	private void ComputeAngles(int prize)
    {
		var rounds = Random.Range(3, 6);

		float degrees = prize * 360 / slices;
		totalAngle = 360 * rounds + (360 - degrees);

		currentAngle = 360 - GetAbsoluteAngle(this.transform.localRotation.eulerAngles.z);
	}

	private float GetAbsoluteAngle(float angle)
    {
		float ans = angle;
		if (ans < 0)
        {
			ans += 360;
        }

		return ans;
    }

    // Update is called once per frame
    void Update()
    {
    	if (enable) {
    		Rotate();
    	}
    }

	void Rotate()
    {
    	float gap = totalAngle - currentAngle;
		if (gap > 0) 
		{
			RotateByGap(gap);
		}
    }

    void RotateByGap(float gap)
    {
		speed = Mathf.Max(gap, MIN_SPEED);
		speed = Mathf.Min(speed, MAX_SPEED);

		// Determine current step
		float step = speed * Time.deltaTime;

		if (currentAngle + step >= totalAngle) 
		{
			step = totalAngle - currentAngle;
		}

		// Rotate the transform
		RotateTransform(step);

		// Accumulate angles
		currentAngle += step;

		if (currentAngle >= totalAngle)
		{
			Deactivate();
			finishEvent.Invoke();
		}
    }

	void RotateTransform(float step) 
	{
		transform.Rotate(-Vector3.forward * step);
	}

	public void Activate(int prize)
	{
		ComputeAngles(prize);
		enable = true;
	}
}
