package com.callumcarmicheal.solar.objects;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Sun extends IPlanet {
	
	
	@Override
	public void init() {
		
		planetName = "The Sun";
		orbitIndex = 0;
		size = 10;
		Color = new Vector3f(1f, 1f, 0f);
		subplanets = null;
		subplanets_Multiplier = 4;
		dayMultiplier = 1;
		subplanets_Multiplier = 4;
		subplanets_offset = 0.7f;
	}
	
	
	
	

}
