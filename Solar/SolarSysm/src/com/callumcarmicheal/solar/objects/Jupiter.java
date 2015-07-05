package com.callumcarmicheal.solar.objects;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Jupiter extends IPlanet {

	
	@Override 
	public void init() {
		
		this.planetName = "Jupiter";
		this.orbitIndex = 5;
		this.size = 318;
		this.Color = new Vector3f(230f, 190f, 83f);
		
		// ADD MOONS
		
		
		distanceFromSun = 5.20f;
	}
	
	@Override
	public float getDay(float day) {
		return day;
	}
}
