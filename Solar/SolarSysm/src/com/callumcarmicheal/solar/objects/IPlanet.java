package com.callumcarmicheal.solar.objects;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.solar.maths.Vector3f;

public class IPlanet {

	private String planetName;
	private int orbitIndex;
	private float Size;
	private float Offset;

	private Vector3f Color;
	
	// If this is a moon or rotates around a certain planet then do it!
	// IF NULL THEN SPIN AROUND THE SUN!
	private IPlanet BasePlanet = null;
	List<IPlanet> Subplanets;
	private float Subplanets_Multiplier = 2;

	public IPlanet() {
		Color = new Vector3f(1.0f, 1.0f, 1.0f);
		
		init();
	}

	public IPlanet(String PlanetName, int OrbitIndex, float Size, float Offset) {}

	public IPlanet(String PlanetName, int OrbitIndex, float Size, float Offset,
			IPlanet BasePlanet, List<IPlanet> Subplanets) {}

	public IPlanet(String PlanetName, int OrbitIndex, float Size, float Offset,
			List<IPlanet> Subplanets) {}
	
	

	// Set size, float offset and more. create and add planets
	public void init() {
		
		
		
		
	}

	// Render and change anything that is needed
	public void update(float HourOfDay, float DayOfYear) {
		
		render(HourOfDay, DayOfYear);
	}

	private void render(float HourOfDay, float DayOfYear) {
		float angle1;
		float angle2;

		if (BasePlanet == null) {
			// Render planet as its own
			{
				// Use DayOfYear to determine its position
				GL11.glRotatef(
						(float) ((360.0 * DayOfYear * (Offset)) / 365.0), 0.0f,
						1.0f, 0.0f);
				GL11.glTranslatef(4.0f, 0.0f, 0.0f);

				GL11.glPushMatrix(); // Save Matrix State
				{
					GL11.glRotatef((float) (360.0 * HourOfDay / 24.0), 0.0f,
							1.0f, 0.0f);

					// Third, we draw earth as a Sphere
					if(Color != null) {
						GL11.glColor3f(Color.R, Color.G, Color.B);
					}
					GLUT.WireSphere3F(0.4f, 10, 10);
				}
				GL11.glPopMatrix(); // Restore Matrix State
			}
		} else {
			{
				GL11.glRotatef((float) 360.0 * DayOfYear / 365.0f, 0.0f, 1.0f,
						0.0f);
				GL11.glTranslatef(0.7f, 0.0f, 0.0f);
				if(Color != null) {
					GL11.glColor3f(Color.R, Color.G, Color.B);
				}
				GLUT.WireSphere3F(BasePlanet.Size / Subplanets_Multiplier, 5, 5);
			}
		}

	}

}
