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
		Mass = 1;
		Color = new Vector3f(0f, 0f, 1f);
		
		subPlanets.add(
			new Planet("Earth (Moon: 0)", this, new Vector3f(1f, 1f, 1f), 0.12f, null)
		);
		
		subPlanetsDistance = 0.07f;
		printDebug = false;
		distanceFromSun = 1;
	}
	
	@Override
	public void update(float HourOfDay, float DayOfYear, int NumberOfYear) {
		this.Mass = 1;
		
		super.update(HourOfDay, DayOfYear, NumberOfYear);
	}
	
}
