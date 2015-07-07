package com.callumcarmicheal.solar.objects;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Jupiter extends IPlanet {

	
	@Override 
	public void init() {
		
		this.planetName = "Jupiter";
		this.orbitIndex = 5;
		this.Mass = 318;
		this.Color = new Vector3f(230f, 190f, 83f);
		
		/*/ ADD MOONS
		for(int x = 1; x < 63; x++) {
			subPlanets.add(
				new Planet("Saturn (Moon: " + (x - 1) + ")", this, new Vector3f(1f, x, 1f), (318 / 2 / x), null)
			);
		} //*/
		
		subPlanetsDistance = (this.Mass + 15.0f);
		
		distanceFromSun = 5.20f;
	}
	
	@Override
	public float getDay(float DayOfYear) {
		return (float) (DayOfYear * 11.86);
	}
}
