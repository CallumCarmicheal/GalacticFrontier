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
	
	//public static int groundRenderIndex = GL11.glGenLists(1);
	
	
	public static void drawGround() { // OPENGL.... Y U NO WOK! ( I FORGOT TO CALL THE FUNCTION -_-)
	
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		float extent      = 15.0f; // How far on the Z-Axis and X-Axis the ground extend
	    float stepSize    = 2f;  // The size of the separation between points
	    float groundLevel = -2f;   // Where on the Y-Axis the ground is drawn
	 
	    // Set colour to white
	    GL11.glColor4f(255, 255, 255, 5);
	 
	    /*/ Draw our ground grid
	    GL11.glBegin(GL11.GL_LINES); {
		    for (float loop = -extent; loop < extent; loop += stepSize)
		    {
		        // Draw lines along Z-Axis
		        GL11.glVertex3f(loop, groundLevel,  extent);
		        GL11.glVertex3f(loop, groundLevel, -extent);
		 
		        // Draw lines across X-Axis
		        GL11.glVertex3f(-extent, groundLevel, loop);
		        GL11.glVertex3f(extent,  groundLevel, loop);
		    }
	    } GL11.glEnd(); */
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    
	    GL11.glColor4f(1, 1, 1, 1);
	}
	
	public static void fixCamera() { // Fix camera (somehow)
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glColor4f(255, 255, 255, 5);
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glColor4f(1, 1, 1, 1);
	}
}
