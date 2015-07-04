package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;
import java.util.List;

import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public class Earth extends IPlanet {
	
	@Override
	public void init() {
		
		planetName = "Earth";
		orbitIndex = 3;
		size = 4;
		Color = new Vector3f(0f, 0f, 1f);
		subPlanets.add(
			new Planet(
				"Earth (Moon)", 
				3, 
				0, 
				new Vector3f(1.0f, 1.0f, 1.0f), 
				this, 
				null,
				4 
			)
		);
		subPlanets_Multiplier = 4;
		subPlanets_offset = 0.7f;
		
		printDebug = false;
		
		// Lets try to add a sub planet
		try {
			
			IPlanet moon;
			moon = getChildPlanet("Earth (Moon)");
			moon.addChildPlanet(new Planet("Earth (Moon (TEST PLANET))", new Vector3f(0, 1, 0), moon));
		} catch (PlanetException e) {
			e.printERR();
		}
		
	}
	
}
