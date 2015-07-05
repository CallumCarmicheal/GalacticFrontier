package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;

import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public class Sun extends IPlanet {
	
	
	@Override
	public void init() {
		
		planetName = "The Sun";
		orbitIndex = 0;
		size = 1000; // 1 = Earth's mass, 1 * Mass
		Color = new Vector3f(1f, 1f, 0f);
		subPlanets = new ArrayList<IPlanet>();
		subPlanets_Multiplier = 4;
		subPlanets_Multiplier = 4;
		subPlanets_offset = 0.7f;
		
		
		try {
			this.addChildPlanet(
				new Planet(
					"Sun (CORE)",
					new Vector3f(255, 245, 242),
					this,
					15
				)
			);
		} catch (PlanetException e) {
			e.printERR();
		}
	}

}
