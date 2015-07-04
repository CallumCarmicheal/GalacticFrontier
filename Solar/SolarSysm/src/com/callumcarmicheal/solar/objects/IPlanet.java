package com.callumcarmicheal.solar.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AsyncBoxView.ChildLocator;

import org.lwjgl.opengl.GL11;
import org.objectweb.asm.tree.IntInsnNode;
import org.pushingpixels.substance.internal.contrib.randelshofer.quaqua.colorchooser.PaletteListModel;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.solar.Main;
import com.callumcarmicheal.solar.exceptions.ExCause;
import com.callumcarmicheal.solar.exceptions.PlanetException;
import com.callumcarmicheal.solar.maths.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public abstract class IPlanet {

	// REQUIRED
	protected String planetName;
	protected int orbitIndex;
	protected float size;
	protected float offset;
	protected Vector3f Color;

	// ** OPTIONAL
	protected IPlanet BasePlanet = null; // IF NULL THEN SPIN AROUND THE SUN
	protected List<IPlanet> subPlanets = new ArrayList<IPlanet>();;
	protected float subPlanets_Multiplier = 4;
	protected float subPlanets_offset = 0.7f;
	protected boolean printDebug = false;

	public IPlanet() {
		init();

		calculateOffset();
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor) {
		this(PlanetName, OrbitIndex, Size, planetColor, null, null, 0.7f);
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor, List<IPlanet> Subplanets,
			float Subplanets_offset) {
		this(PlanetName, OrbitIndex, Size, planetColor, null, Subplanets,
				Subplanets_offset);
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size,
			Vector3f planetColor, IPlanet BasePlanet, List<IPlanet> Subplanets,
			float Subplanets_Multiplier) {
		this.planetName = PlanetName;
		this.orbitIndex = OrbitIndex;
		this.size = Size;
		this.Color = planetColor;
		this.BasePlanet = BasePlanet;
		this.subPlanets = Subplanets;
		this.subPlanets_Multiplier = Subplanets_Multiplier;

		init();

		calculateOffset();
	}

	private void calculateOffset() {
		if (BasePlanet != null) {
			this.offset = 100.0f;
		} else {
			this.offset = subPlanets_offset;
		}
	}

	/**
	 * Get the current day of the year inside the planet
	 * 
	 * @param DayOfYear
	 * @return
	 */
	public float getDay(float DayOfYear) {
		return (360.0f * DayOfYear / 365.0f);
	}

	/**
	 * Not used in current version of simulation Get the current hour using the
	 * planets D/N Cycle
	 * 
	 * @param HourOfDay
	 *            The current hour on earth
	 * @return Planets Hour
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
	 * DO NOT OVERWRITE UNLESS NEEDED TO, AFTER CALL super.render(HourOfDay,
	 * DayOfYear);
	 */
	private void render(float HourOfDay, float DayOfYear, int NumberOfYear) {
		float angle1;
		float angle2;

		if (orbitIndex == 0) {
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f, 0.0f, -8.0f);
			GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);

			if (Color != null) {
				GL11.glColor3f(Color.r, Color.g, Color.b);
			}
			GLUT.WireSphere3D((this.size / 10), 15, 15);
		} else if (BasePlanet == null) {
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f, 0.0f, -8.0f);
			GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);

			// Render planet as its own
			{
				// Get Planet time of Day (DEFAULT : EARTH)
				GL11.glRotatef(getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef((2 * orbitIndex - 1 + offset), 0.0f, 0.0f);

				// GL11.glRotatef(HourOfDay, 0.0f, 1.0f, 0.0f);
				GL11.glPushMatrix(); // Save Matrix State
				{
					// now draw the Planet as a Sphere
					if (Color != null) {
						GL11.glColor3f(Color.r, Color.g, Color.b);
					}
					GLUT.WireSphere3F((this.size / 10), 10, 10);
				}
				GL11.glPopMatrix(); // Restore Matrix State

				
				// Render the prediction line
				GL11.glPushMatrix(); {
					for (float calcDay = 0.0f; calcDay < 365; calcDay += (24.0f / 100.0f)) {

						GL11.glLoadIdentity();
						GL11.glColor3f(1, 1, 1);
						GL11.glTranslatef(0.0f, 0.0f, -8.0f);
						GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
						GL11.glRotatef(calcDay, 0.0f, 1.0f, 0.0f);
						GL11.glTranslatef((2 * orbitIndex - 1 + offset), 0.0f,
								0.0f);
						
						GL11.glPointSize(1f);
						GL11.glBegin(GL11.GL_POINTS); { 
							GL11.glVertex3f(0, 0, 0);
						} GL11.glEnd();
					}
				} GL11.glPopMatrix();
				
				

				if (printDebug) {
					String childOuput = "	Child Planets   :- \n" + "		None";

					if (!subPlanets.isEmpty()) {
						childOuput = "	Child Planets   :- ";
					}

					// Sigh (WHY WONT YOU LINE UP)
					String debugOutput = (this.planetName + " :- \n"
							+ "	Location        :- " + "\n"
							+ "		Rotation    :  (" + (getDay(DayOfYear))
							+ ", 0.0, 1.0, 0.0) \n" + "		Translate   :  ("
							+ (2 * orbitIndex - 1 + offset) + ", 0.0, 0.0) \n"
							+ "	Color           :  " + Color.toString() + "\n"
							+ "	Orbit Index     :  " + this.orbitIndex + "\n"
							+ "	Offset          :  " + this.offset + "\n"
							+ "	Child Offset    :  " + this.subPlanets_offset
							+ "\n" + "	ChildMultiplier :  "
							+ this.subPlanets_Multiplier + "\n" + childOuput);

					System.out.println(debugOutput);
				}
			}
		} else {
			GL11.glRotatef(BasePlanet.getDay(DayOfYear), 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.7f, 0.0f, 0.0f);

			if (Color != null) {
				GL11.glColor3f(Color.r, Color.g, Color.b);
			}

			GLUT.WireSphere3F(
					((BasePlanet.size / 10) / BasePlanet.subPlanets_Multiplier)
							/ BasePlanet.subPlanets.size(), 5, 5);

			if (BasePlanet.printDebug) {
				String childOuput = " (C) Child Planets :- \n" + "			None";

				if (!subPlanets.isEmpty()) {
					childOuput = " (C) Child Planets :- ";
				}

				String debugOutput = ("		" + this.planetName + " :- \n"
						+ "	(C) Location :- " + "\n"
						+ "	(C) Translate :  (0.7, 0.0, 0.0) \n"
						+ "	(C) Color :  " + Color.toString() + "\n" + childOuput);

				System.out.println(debugOutput);
			}
			
			/*/ RENDER PREDICTION LINE
			
			for (float calcDay = 0.0f; calcDay < 24; calcDay += (12.0f / 100.0f)) {

				//I just realised this would not work for a sub planet
				//	that is a sub planet of a sub planet so on so on...
				
				// set to base planet location
				GL11.glRotatef(calcDay, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(0.05f, 0.0f, 0.0f);
				
				GL11.glPointSize(1f);
				GL11.glBegin(GL11.GL_POINTS); { 
					GL11.glVertex3f(0, 0, 0);
				} GL11.glEnd();
			} // */
		}

	}

}
