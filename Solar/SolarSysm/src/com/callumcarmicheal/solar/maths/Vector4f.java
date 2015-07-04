package com.callumcarmicheal.solar.maths;

public class Vector4f {

	public float FOV;
	public float ASPECT;
	public float NEAR;
	public float FAR;
	
	public float vec1;
	public float vec2;
	public float vec3;
	public float vec4;
	
	
	public Vector4f(float v1, float v2, float v3, float v4) {
		FOV = v1;
		vec1 = v1;
		
		ASPECT = v2;
		vec2 = v2;
		
		NEAR = v3;
		vec3 = v3;
		
		FAR = v4;
		vec4 = v4;
	}
	
	@Override
	public String toString(){ 
		return "Vector4f ( " + vec1+ " | " + vec2 + " | " + vec3 + " | " + vec4 + " )";
	}
}
