package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.solar.Main;
import com.callumcarmicheal.solar.exceptions.ExCause;
import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

public abstract class IPlanet {

	// DO NOT TOUCH (RENDER AND SIMULATION VARS)
	protected int SizeMultiplier = 4;
	protected float DistFromSun    = 1100f;
	
	// REQUIRED
	protected String planetName;
	protected int orbitIndex;
	protected float Mass;
	protected Vector3f Color;
	protected float distanceFromSun;
	
	// ** OPTIONAL
	protected IPlanet BasePlanet = null; // IF NULL THEN SPIN AROUND THE SUN
	protected List<IPlanet> subPlanets = new ArrayList<IPlanet>();;
	protected float subPlanetsDistance = 0.07f; 
	protected float subPlanetsMultiplier = 4;
	protected boolean printDebug = false;

	public IPlanet() {
		init();
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor) {
		this(PlanetName, OrbitIndex, Size, planetColor, null, 0.7f);
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor, List<IPlanet> Subplanets,
			float Subplanets_offset) {
		this(PlanetName, OrbitIndex, Size, planetColor, null, Subplanets);
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor, IPlanet BasePlanet, List<IPlanet> Subplanets) {
		this.planetName = PlanetName;
		this.orbitIndex = OrbitIndex;
		this.Mass = Size;
		this.Color = planetColor;
		this.BasePlanet = BasePlanet;
		this.subPlanets = Subplanets;

		init();
	}

	/**
	 * Get the current day of the year inside the planet
	 * 
	 * @param DayOfYear
	 * @return
	 */
	public float getDay(float DayOfYear) {
		
		if(BasePlanet == null) {
			return (360.0f * DayOfYear / 365.0f);
		} else {
			return BasePlanet.getDay(DayOfYear);
		}
	}

	/**
	 * Not used in current version of simulation Get the current hour using the
	 * planets D/N Cycle
	 * 
	 * @param HourOfDay
	 *            The current hour on earth
	 * @return The Planet's Hour
	 */
	public float getHour(float HourOfDay) {
		return (360.0f * HourOfDay / 24.0f);
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
			throw new PlanetException(subPlanet, ExCause.PlanetAlreadyTaken,
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
	 * Any extra code needed add it in here
	 */
	public abstract void init();

	/**
	 * Called before render
	 */
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

	/**
	 * DO NOT OVERWRITE UNLESS NEEDED TO, AFTER CALL super.render();
	 */
	private void render(float HourOfDay, float DayOfYear, int NumberOfYear) {
		float angle1;
		float angle2;

		if (orbitIndex == 0) {
			GL11.glLoadIdentity();
			GL11.glTranslatef(3.0f, 0.0f, -8.0f);
 			GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);

			if (Color != null) {
				GL11.glColor3f(Color.r, Color.g, Color.b);
			}
			GLUT.WireSphere3D(1 * this.Mass / SizeMultiplier + 5, 15, 15);
		} else if (BasePlanet == null) {
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f, 0.0f, -8.0f);
			GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);

			// Render planet as its own
			{
				// Get Planet time of Day (DEFAULT : EARTH)
				GL11.glRotatef(getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef((DistFromSun * distanceFromSun), 0.0f, 0.0f);

				// GL11.glRotatef(HourOfDay, 0.0f, 1.0f, 0.0f);
				GL11.glPushMatrix(); // Save Matrix State
				{
					// now draw the Planet as a Sphere
					if (Color != null) {
						GL11.glColor3f(Color.r, Color.g, Color.b);
					}
					GLUT.WireSphere3F((1 * this.Mass / SizeMultiplier), 10, 10);
				}
				GL11.glPopMatrix(); // Restore Matrix State

				
				// Render the prediction line
				if(Main.instance.projectionLines) {
					GL11.glPushMatrix(); {
						for (float calcDay = 0.0f; calcDay < 365; calcDay += (24.0f / 100.0f)) {
	
							GL11.glLoadIdentity();
							GL11.glColor3f(1, 1, 1);
							GL11.glTranslatef(0.0f, 0.0f, -8.0f);
							GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
							GL11.glRotatef(calcDay, 0.0f, 1.0f, 0.0f);
							GL11.glTranslatef((DistFromSun * distanceFromSun), 0.0f,
									0.0f);
							
							GL11.glPointSize(0.1f);
							GL11.glBegin(GL11.GL_POINTS); { 
								GL11.glVertex3f(0, 0, 0);
							} GL11.glEnd();
						}
					} GL11.glPopMatrix();
				}
				

				if (printDebug) { /* TODO: Create a debug output for planets */ }
			}
		} else {
			//GL11.glLoadIdentity();
			//GL11.glTranslatef(0.0f, 0.0f, -8.0f);
			//GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
			//GL11.glRotatef(BasePlanet.getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
			GL11.glRotatef((DayOfYear), 0.0f, 1.0f, 0.0f);
			//GL11.glTranslatef((BasePlanet.DistFromSun * BasePlanet.distanceFromSun), 0.0f, 0.0f);
			GL11.glTranslatef(BasePlanet.subPlanetsDistance * SizeMultiplier + 0.25f, 0.0f, 0.0f);

			if (Color != null) {
				GL11.glColor3f(Color.r, Color.g, Color.b);
			}

			GLUT.WireSphere3F((1 * (this.BasePlanet.Mass - this.Mass) / SizeMultiplier), 5, 5);

			if (BasePlanet.printDebug) { /* TODO: Create a debug output for planets */ }
			
			/*/ Render the prediction line (This was accident but a cool one) now does not work o.0
			GL11.glPushMatrix(); { 
				for (float calcDay = 0.0f; calcDay < 365; calcDay += (24.0f / 10.0f)) {

					GL11.glLoadIdentity();
					GL11.glColor3f(1, 1, 1);
					GL11.glTranslatef(0.0f, 0.0f, -8.0f);
					GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
					GL11.glRotatef(BasePlanet.getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
					GL11.glTranslatef((DistFromSun * distanceFromSun), 0.0f, 0.0f);
					GL11.glTranslatef(BasePlanet.subPlanets_offset * SizeMultiplier + 0.25f, 0.0f, 0.0f);
					
					GL11.glPointSize(1f);
					GL11.glBegin(GL11.GL_POINTS); { 
						GL11.glVertex3f(0, 0, 0);
					} GL11.glEnd();
				}
			} GL11.glPopMatrix(); //*/
		}

	}
	
	/*
	 // BLACKHOLE SWIRLY THING!
		for (float calcDay = 0.0f; calcDay < 24; calcDay += (12.0f / 20.0f)) {

			//I just realised this would not work for a sub planet
			//	that is a sub planet of a sub planet so on so on...
			// set to base planet location
			GL11.glRotatef(calcDay, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.05f, 0.0f, 0.0f);
				
			GL11.glPointSize(1f);
			GL11.glBegin(GL11.GL_POINTS); { 
				GL11.glVertex3f(0, 0, 0);
			} GL11.glEnd();
		} // 
	 */

}
