package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Earth extends IPlanet {
	
	public Earth(){}
	
	@Override
	public void init() {
		
		planetName = "Earth";
		orbitIndex = 3;
		size = 4;
		Color = new Vector3f(0f, 0f, 1f);
		subplanets.add(new Planet("Earth (Moon)", 3, 0, 0, new Vector3f(1.0f, 1.0f, 1.0f), this, null, 4 ));
		subplanets_Multiplier = 4;
		dayMultiplier = 1;
		subplanets_Multiplier = 4;
		subplanets_offset = 0.7f;
		
	}
	
}
