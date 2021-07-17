using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RotateSelf : MonoBehaviour
{
	private const float MIN_SPEED = 10.0f;
	private const float MAX_SPEED = 800.0f;
	private float currentAngle = 0.0f;
	private float totalAngle;

	private int prize = 6;
	private float slices = 8;
	private float rounds = 5;
	private float speed;

	private bool enable = false;

    // Start is called before the first frame update
    void Start()
    {
    	Reset();
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
        	Enable();
        }

    	if (enable) {
    		Rotate();
    	}
    }

    public void Enable() {
    	enable = true;
    }

    void Reset() {
    	enable = false;
    	float degrees = - (prize - slices) * 360 / slices - (360/slices);
    	totalAngle = 360 * rounds + degrees;
    	Debug.Log(totalAngle);
    	currentAngle = 0.0f;
    }

    void Rotate() {
    	float gap = totalAngle - currentAngle;
		if (gap > 0) 
		{
			RotateByGap(gap);
		}
    }

    void RotateByGap(float gap) {
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

		if (currentAngle >= totalAngle)
		{
			Reset();
		}
    }

	void RotateTransform(float step) 
	{
		transform.Rotate(-Vector3.forward * step);
	}
}
