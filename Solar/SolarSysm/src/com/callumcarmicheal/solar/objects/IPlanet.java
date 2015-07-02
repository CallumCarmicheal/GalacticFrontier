package com.callumcarmicheal.solar.objects;

import org.lwjgl.opengl.GL11;

import com.callumcarmicheal.OpenGL.GLUT;

public class IPlanet {

	private String planetName;
	private int orbitIndex;
	private int Size;

	// If this is a moon or rotates around a certain planet then do it!
	// IF NULL THEN SPIN AROUND THE SUN!
	private IPlanet BasePlanet = null;

	public IPlanet() {
		init();
	}

	public void init() {
	}

	public void update(float HourOfDay, float DayOfYear) {
	}

	private void render(float HourOfDay, float DayOfYear) {

		if(BasePlanet != null) {
			
		}
		
		float angle1;
		float angle2;
		
		// Earth
		{
			// Draw the Earth
			// First position it around the sun
			// Use DayOfYear to determine its position
			GL11.glRotatef((float) (360.0 * DayOfYear / 365.0), 0.0f, 1.0f,
					0.0f);
			GL11.glTranslatef(4.0f, 0.0f, 0.0f);

			GL11.glPushMatrix(); // Save Matrix State
			{
				// Second, rotate earth on its axis
				// Once again we will use HourOfDay to determine its rotation
				GL11.glRotatef(
					(float) (360.0 * HourOfDay / 24.0), 
					0.0f, 
					1.0f,
					0.0f
				);

				// Third, we draw earth as a Sphere
				GL11.glColor3f(0.2f, 0.2f, 1.0f);
				GLUT.WireSphere3F(0.4f, 10, 10);
			}
			GL11.glPopMatrix(); // Restore Matrix State
		}

	}

}
