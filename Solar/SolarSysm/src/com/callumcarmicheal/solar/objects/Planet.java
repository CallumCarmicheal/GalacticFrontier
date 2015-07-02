package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Planet extends IPlanet{

	public Planet(String PlanetName, int OrbitIndex, float Size, float DayMultiplier, Vector3f planetColor) {
		super(PlanetName, OrbitIndex, Size, DayMultiplier, planetColor,  null, null, 0.7f);
	}

	public Planet(String PlanetName, int OrbitIndex, float Size, float DayMultiplier, Vector3f planetColor, List<IPlanet> Subplanets, float Subplanets_offset) {
		super(PlanetName, OrbitIndex, Size, DayMultiplier, planetColor,  null, Subplanets, Subplanets_offset);
	}
	
	public Planet(String PlanetName, int OrbitIndex, float Size, float DayMultiplier, Vector3f planetColor, IPlanet BasePlanet, List<IPlanet> Subplanets, float Subplanets_Multiplier) {
		super(PlanetName, OrbitIndex, Size, DayMultiplier, planetColor, BasePlanet, Subplanets, Subplanets_Multiplier);
	}
	
	
	@Override
	public void init() {}

}
