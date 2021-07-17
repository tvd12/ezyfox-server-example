using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Rotate : MonoBehaviour
{
	private const float MIN_SPEED = 10.0f;
	private const float MAX_SPEED = 800.0f;
	private float currentAngle = 0.0f;
	private float totalAngle;

	private int prize = 6;

	private float slices = 8;
	private float rounds = 5;
	private float speed;

    // Start is called before the first frame update
    void Start()
    {
    	float degrees = - (prize - slices) * 360 / slices - (360/slices)/2;
    	totalAngle = 360 * rounds + degrees;
    	Debug.Log(totalAngle);
    }

    // Update is called once per frame
    void Update()
    {
		float gap = totalAngle - currentAngle;

		if (gap > 0) 
		{
			RotateWheel(gap);
		}
    }

    void RotateWheel(float gap) {
		speed = Mathf.Max(gap, MIN_SPEED);
		speed = Mathf.Min(speed, MAX_SPEED);

		// Determine current step
		float step = speed * Time.deltaTime;

		if (currentAngle + step >= totalAngle) 
			step = totalAngle - currentAngle;

		// Rotate the transform
		RotateTransform(step);

		// Accumulate angles
		currentAngle += step;
    }

	void RotateTransform(float step) 
	{
		transform.Rotate(-Vector3.forward * step);
	}
}
