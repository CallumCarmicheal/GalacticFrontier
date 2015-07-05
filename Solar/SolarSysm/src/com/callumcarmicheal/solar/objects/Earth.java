package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public class Earth extends IPlanet {
	
	@Override
	public void init() {
		
		planetName = "Earth";
		orbitIndex = 3;
		size = 1;
		Color = new Vector3f(0f, 0f, 1f);
		
		subPlanets.add(
			new Planet(
				"Earth (Moon)", 
				3, 
				1.222f,  // 1.2223321763038349416210803742011e-26f
				new Vector3f(1.0f, 1.0f, 1.0f), 
				this, 
				null,
				4 
			)
		);
		
		subPlanets_Multiplier = 2;
		subPlanets_offset = 0.07f;
		
		printDebug = false;
		
		distanceFromSun = 1;
	}
	
}
