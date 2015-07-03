package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Mercury extends IPlanet {
	
	@Override
	public void init() {
		
		planetName = "Mercury";
		orbitIndex = 1;
		size = 1;
		Color = new Vector3f(105f, 79f, 6f);
		//subplanets.add(new Planet("", 3, 0, 0, new Vector3f(1.0f, 1.0f, 1.0f), this, null, 4 ));
		dayMultiplier = 1;
		subplanets_Multiplier = 4;
		subplanets_offset = 0.7f;
		
		dayMultiplier = 7f;
		
	}
	
}
