package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Mercury extends IPlanet {
	
	@Override
	public void init() {
		
		planetName = "Mercury";
		orbitIndex = 1;
		size = 1;
		Color = new Vector3f(201, 131, 18);
		//subplanets.add(new Planet("", 3, 0, 0, new Vector3f(1.0f, 1.0f, 1.0f), this, null, 4 ));
		subPlanets_Multiplier = 4;
		subPlanets_offset = 0.7f;
		
		//printDebug = true;
	}
	
	
	@Override
	public float getDay(float DayofYear) {
		//return (DayofYear * (((58 * 7) + (18) + (30 / 60)) / 24)); (CANT REMEMBER HOW I GOT THIS)
		
		/* The Algorithm works a-bit like this
		 * 
		 * 	Rotation = 
		 * 		Day of Year (earths day of year 1 - 365)
		 * 			Multiplied by 
		 * 				57 (-1 for earth, mercury = 58) days 
		 * 				add 15 / 10 to get Hours in day form
		 * 				add 30 / 100 to get Minutes in day form		
		 * 				(X) Divided by 10 to slow it down to real time
		 */
		
		return (DayofYear * ((57) + (15 / 10) + (30 / 100)) / 10);
	}
	
	
}
