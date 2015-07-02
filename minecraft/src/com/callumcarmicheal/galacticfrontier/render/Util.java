package com.callumcarmicheal.galacticfrontier.render;

import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Util {

	public static void drawRing(float roation) {
		float radius1 = 0.5f;
		float radius2 = 0.8f;
		float x, y, z;
		double c, s;
		double t = 2 * Math.PI / 30;
		
		GL11.glRotatef(roation, 0f, 0f, 0f);

		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		for (int i = 0; i <= 30; i++) {
			c = Math.cos(i * t);
			s = Math.sin(i * t);
			GL11.glColor4f(0.5f, 0.5f, 0.0f, 1.0f);
			GL11.glVertex3f((float) (radius1 * c), (float) (radius1 * s), 0);
			GL11.glColor4f(0.5f, 0.5f, 0, 0);
			GL11.glVertex3f((float) (radius2 * c), (float) (radius2 * s), 0);

		}
		GL11.glEnd();
	}

	public static void plain() {
		float s = 2.3f;
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glTranslatef(-5 * s, -2.0f, 5 * s);
		for (int j = 0; j < 10; j++) {
			GL11.glBegin(GL11.GL_QUAD_STRIP);
			for (int i = 0; i < 10; i++) {
				if ((i + j) % 2 == 0)
					GL11.glColor3f(0, 0, 0);
				else

					GL11.glColor3f(0.1f, 0.1f, 0.1f);

				GL11.glVertex3f(j * s, 0, -1 * s * i);
				GL11.glVertex3f(s + j * s, 0, -1 * s * i);
			}

			GL11.glEnd();
		}
		GL11.glTranslatef(5 * s, 2.0f, -5 * s);
	}
	
	public static void make2D() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0f, Display.getWidth() * 0.5, Display.getHeight() * 0.5, 0.0f, 0.0f, 1.0f);

		//GalacticFrontierMOD.getLog().INFO();
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public static void make3D() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(0.0f,
				((float) Display.getWidth() / (float) Display.getHeight()), 0.1f, 100.0f); // Calculate
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public static void drawSphere(float r, float g, float b) 				 { drawSphere( r, g, b, 2, 0.5); }
	public static void drawSphere(float r, float g, float b, int complexity) { drawSphere(r, g, b, complexity, 0.5); }
	public static void drawSphere(float r, float g, float b, double radius ) { drawSphere(r, g, b, 2, radius); }
	public static void drawSphere(float r, float g, float b, int complexity, double radius) {
		double cda, sda;
		double radius1 = 0;
		double radius2 = 0;

		double angle = 0;
		int segments = complexity;
		double dAngle = (Math.PI / segments);

		float x = 0;
		float y = 0;
		float z = 0;
		float z1, z2, c2 = 0;
		float c1 = 0;

		GL11.glShadeModel(GL11.GL_SMOOTH);

		for (int i = 0; i < segments; i++) {
			angle = Math.PI / 2 - i * dAngle;
			radius1 = radius * Math.cos(angle);
			z1 = (float) (radius * Math.sin(angle));
			c1 = (float) ((Math.PI / 2 + angle) / Math.PI);

			angle = Math.PI / 2 - (i + 1) * dAngle;
			radius2 = radius * Math.cos(angle);
			z2 = (float) (radius * Math.sin(angle));

			c2 = (float) ((Math.PI / 2 + angle) / Math.PI);

			GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
			GL11.glColor3f(r, g, b);
			for (int j = 0; j <= 2 * segments; j++) {
				cda = Math.cos(j * dAngle);
				sda = Math.sin(j * dAngle);
				x = (float) (radius1 * cda);
				y = (float) (radius1 * sda);
				
				// GL11.glColor3f(r * c1, g * c1, b * c1);
				GL11.glVertex3f(x, y, z1);
				x = (float) (radius2 * cda);
				y = (float) (radius2 * sda);
				
				// GL11.glColor3f(r * c2, g * c2, b * c2);
				GL11.glVertex3f(x, y, z2);

			}
			GL11.glEnd();
		}
	}
}
