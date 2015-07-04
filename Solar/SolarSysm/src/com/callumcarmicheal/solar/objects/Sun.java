package com.callumcarmicheal.solar.objects;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Sun extends IPlanet {
	
	
	@Override
	public void init() {
		
		planetName = "The Sun";
		orbitIndex = 0;
		size = 20; // 1 = Earth's mass, 1 * Mass
		//size = 3.03f;
		Color = new Vector3f(1f, 1f, 0f);
		subPlanets = null;
		subPlanets_Multiplier = 4;
		subPlanets_Multiplier = 4;
		subPlanets_offset = 0.7f;
	}
	
	
	
	

}
