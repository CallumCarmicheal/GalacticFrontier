package com.callumcarmicheal.old.solar.objects;

import java.util.ArrayList;

import com.callumcarmicheal.maths.Vector3f;

public class Mars extends IPlanet {
	
	@Override
	public void init() {
		planetName = "Mars";
		orbitIndex = 4;
		Mass = (0.107f);
		
		Color = new Vector3f(1f, 1f, 0f);
		subPlanets = null;
		subPlanetsDistance = 0.07f;
		
		distanceFromSun = 1.52f;
		
		subPlanets = new ArrayList<IPlanet>();
		
		subPlanets.add(
			new Moon(this, "Phobos", 1, 0.108f)
		);
		
		subPlanets.add(
			new Moon(this, "Deimos", 2, 0.2f)
		);
		
		printDebug = false;
	}
	
	@Override 
	public float getDay(float DayOfYear) {
		return (float) (DayOfYear * 1.03);	
	}
	
	public class Moon extends IPlanet {

		int MoonIndex;
		
		public Moon(IPlanet basePlanet, String planetName, int MoonIndex, float size) {
			this.BasePlanet = basePlanet;
			this.MoonIndex = MoonIndex;
			this.planetName = planetName;
			this.Mass = size;
			
			init();
		}
		
		@Override
		public void init() {
			this.planetName = "Mars (" + planetName + " | " + MoonIndex + ")";
			this.orbitIndex = 4;
		}
		
		
		@Override
		public float getDay(float DayOfYear) {
			return (float) (BasePlanet.getDay(DayOfYear) +  (MoonIndex * 0.5));
		}
		
	}
	
}


