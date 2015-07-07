package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.solar.exceptions.ExCause;
import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public class Moon extends IPlanet {
	
	public Moon(String PlanetName, IPlanet bP, Vector3f c, float mM, List<IPlanet> sP) {
		this.planetName = PlanetName;
		this.BasePlanet = bP;
		this.Mass = mM;
		this.Color = c;
		this.subPlanets = sP;
		
		this.init();
	}
	
	/**
	 * 
	 * Add a sub Planet
	 * 
	 * @param subPlanet
	 *            The planet to add
	 * @throws PlanetException
	 *             If Planet is already created, this will be thrown
	 */
	public void addChildPlanet(IPlanet subPlanet) throws PlanetException {
		boolean valid = true;

		if (subPlanets != null) {
			for (IPlanet moon : this.subPlanets) {
				if (moon.planetName.equals(subPlanet.planetName)) {
					valid = false;
				}
			}
		} else {
			subPlanets = new ArrayList<IPlanet>();
		}

		if (valid) {
			this.subPlanets.add(subPlanet);
		} else {
			throw new PlanetException(
				subPlanet, ExCause.PlanetAlreadyTaken,
				"The planet's name was already created and therefore was not added");
		}
	}

	/**
	 * Get Planet by planet's name.
	 * 
	 * @param name
	 * @return
	 * @throws PlanetException
	 *             If planet is not in array, this will be thrown
	 */

	public IPlanet getChildPlanet(String name) throws PlanetException {
		if (!subPlanets.isEmpty()) {
			for (IPlanet moon : this.subPlanets) {
				if (moon.planetName.equals(name)) {
					return moon;
				}
			}
		}

		throw new PlanetException(ExCause.PlanetDoesnotExist,
				"The planet specified does not exist in child array.");
	}

	/**
	 * Any extra code needed add it in here, for implemations (OVERRIDE)
	 */
	public void init(){}
	
	
	public void update(float HourOfDay, float DayOfYear, int NumberOfYear) {
		// Render Self
		render(HourOfDay, DayOfYear, NumberOfYear);

		// Render Sub-planets
		if (this.subPlanets != null) {
			if (!this.subPlanets.isEmpty()) {
				for (IPlanet moon : subPlanets) {
					moon.update(HourOfDay, DayOfYear, NumberOfYear);
				}
			}
		}
	}
	
	public void render(float HourOfDay, float DayOfYear, int NumberOfYear) {
		// Now render the moon according to the basePlanet
		GL11.glLoadIdentity();
		setToBaseLocation(HourOfDay, DayOfYear, NumberOfYear);
		
		// Now we need to simulate seasons, somehow.
		
		{
			if (Color != null) {
				GL11.glColor3f(Color.r, Color.g, Color.b);
			}

			GLUT.WireSphere3F(
				1f * (this.Mass / BasePlanet.SizeMultiplier),
				5, 
				5
			);
		}
	}
	
	
	public void setToBaseLocation(float HourOfDay, float DayOfYear, int NumberOfYear) {
		GL11.glTranslatef(0.0f, 0.0f, -8.0f);
		GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(BasePlanet.getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef((BasePlanet.DistFromSun * BasePlanet.distanceFromSun), 0.0f, 0.0f);
	}
}
