package com.callumcarmicheal.solar.objects;

import java.util.List;

import com.callumcarmicheal.solar.maths.Vector3f;

public class Planet extends IPlanet {

	public Planet(String PlanetName, int OrbitIndex, float Mass, Vector3f planetColor) { super(PlanetName, OrbitIndex, Mass, planetColor); }
	public Planet(String PlanetName, int OrbitIndex, float Mass, Vector3f planetColor, List<IPlanet> Subplanets, float Subplanets_offset) { super(PlanetName, OrbitIndex, Mass, planetColor, Subplanets, Subplanets_offset); } 
	public Planet(String PlanetName, int OrbitIndex, float Mass, Vector3f planetColor, IPlanet BasePlanet, List<IPlanet> Subplanets) { super(PlanetName, OrbitIndex, Mass, planetColor, BasePlanet, Subplanets); }
	
	/**
	 * This can be used to create a basic planet or simple moon
	 */
	public Planet(String PlanetName, Vector3f PlanetColor, IPlanet BasePlanet) {
		this.planetName = PlanetName;
		this.Color = PlanetColor;
		this.BasePlanet = BasePlanet;
	}
	
	/**
	 * This can be used to create a basic planet or simple moon
	 */
	public Planet(String PlanetName, IPlanet BasePlanet, Vector3f PlanetColor, float Size, List<IPlanet> Subplanets) {
		this.planetName = PlanetName;
		this.Color = PlanetColor;
		this.BasePlanet = BasePlanet;
		this.Mass = Size;
		this.subPlanets = Subplanets;
	}
	
	
	@Override
	public void init() {}

}
