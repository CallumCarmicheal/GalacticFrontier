package com.callumcarmicheal.OpenGL;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Util {

	
	public static void make2D() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0f, Display.getWidth() * 0.5, Display.getHeight() * 0.5, 0.0f, 0.0f, 1.0f);
		
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
}
