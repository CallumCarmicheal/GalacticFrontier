package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Planet extends IPlanet{

	public Planet(String PlanetName, int OrbitIndex, float Size, Vector3f planetColor) {
		super(PlanetName, OrbitIndex, Size, planetColor,  null, null, 0.7f);
	}

	public Planet(String PlanetName, int OrbitIndex, float Size, Vector3f planetColor, List<IPlanet> Subplanets, float Subplanets_offset) {
		super(PlanetName, OrbitIndex, Size, planetColor,  null, Subplanets, Subplanets_offset);
	}
	
	public Planet(String PlanetName, int OrbitIndex, float Size, Vector3f planetColor, IPlanet BasePlanet, List<IPlanet> Subplanets, float Subplanets_Multiplier) {
		super(PlanetName, OrbitIndex, Size, planetColor, BasePlanet, Subplanets, Subplanets_Multiplier);
	}
	
	/**
	 * This can be used to create a basic planet or simple moon
	 */
	public Planet(String PlanetName, Vector3f PlanetColor, IPlanet BasePlanet, float Subplanets_Multiplier) {
		this.planetName = PlanetName;
		this.Color = PlanetColor;
		this.BasePlanet = BasePlanet;
		this.subPlanets_Multiplier = Subplanets_Multiplier;
	}
	
	/**
	 * This can be used to create a basic planet or simple moon
	 */
	public Planet(String PlanetName, Vector3f PlanetColor, IPlanet BasePlanet) {
		this.planetName = PlanetName;
		this.Color = PlanetColor;
		this.BasePlanet = BasePlanet;
	}
	
	
	@Override
	public void init() {}

}
