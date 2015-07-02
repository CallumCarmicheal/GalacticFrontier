package com.callumcarmicheal.solar.maths;

public class Vector3f {
	
	// Same as RGB but dif context
	public float X;
	public float Y;
	public float Z;
	
	
	// Same as XYZ but dif context
	public float R;
	public float G;
	public float B;
	
	
	public Vector3f(float f1, float f2, float f3) {
		this.X = f1;
		this.R = f1;
		
		this.Y = f2;
		this.G = f2;
		
		this.Z = f3;
		this.B = f3;
	}
}
