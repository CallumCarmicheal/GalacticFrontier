package com.callumcarmicheal.maths;

public class Vector3f {
	
	// Same as RGB but dif context
	public float x;
	public float y;
	public float z;
	
	
	// Same as XYZ but dif context
	public float r;
	public float g;
	public float b;
	
	
	public Vector3f(float f1, float f2, float f3) {
		this.x = f1;
		this.r = f1;
		
		this.y = f2;
		this.g = f2;
		
		this.z = f3;
		this.b = f3;
	}
	
	@Override
	public String toString(){ 
		return "Vector3f ( " + x + " | " + y + " | " + z + " )";
	}
}
