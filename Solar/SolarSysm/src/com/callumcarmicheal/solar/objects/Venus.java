package com.callumcarmicheal.solar.objects;

import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public class Venus extends IPlanet {

	@Override
	public void init() {
		planetName = "Venus";
		orbitIndex = 2;
		size = (0.815f);
		
		Color = new Vector3f(1f, 1f, 0f);
		subPlanets = null;
		subPlanets_Multiplier = 4;
		subPlanets_offset = 0.07f;
		
		distanceFromSun = 0.72f;
		
		printDebug = false;
	}
	
	@Override 
	public float getDay(float DayOfYear) {
		return (float) (DayOfYear * 0.62);	
	}
}
