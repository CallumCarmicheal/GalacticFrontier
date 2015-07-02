package com.callumcarmicheal.OpenGL;

import org.lwjgl.opengl.GL11;

public class GLUT {
	
	public static void WireSphere3F(float r, int nParal, int nMerid){
	    float x, y, z, i, j;
	    for ( j=0; j<Math.PI; j+=Math.PI/(nParal+1)){
	        GL11.glBegin(GL11.GL_LINE_LOOP);
	        y=(float) (r*Math.cos(j));
	        for(i=0; i<2*Math.PI; i+=Math.PI/60){
	            x=(float) (r*Math.cos(i)*Math.sin(j));
	            z=(float) (r*Math.sin(i)*Math.sin(j));
	            GL11.glVertex3f(x,y,z);
	        }
	        GL11.glEnd();
	    }

	    for(j=0; j<Math.PI; j+=Math.PI/nMerid){
	    	GL11.glBegin(GL11.GL_LINE_LOOP);
	        for(i=0; i<2*Math.PI; i+=Math.PI/60){
	            x=(float) (r*Math.sin(i)*Math.cos(j));
	            y=(float) (r*Math.cos(i));
	            z=(float) (r*Math.sin(j)*Math.sin(i));
	            GL11.glVertex3f(x,y,z);
	        }
	        GL11.glEnd();
	    }
	}
	
	public static void WireSphere3D(double r, int nParal, int nMerid){
		WireSphere3F((float)r, nParal, nMerid);
	}
	
	
	public static void Sphere3D(double r, int nParel, int nMerid){}
	public static void drawSphere(int complexity, double radius) 			 				{ drawSphere(0f, 0f, 0f, complexity, radius, false); }
	public static void drawSphere(float r, float g, float b) 				 				{ drawSphere( r, g, b, 2, 0.5); }
	public static void drawSphere(float r, float g, float b, int complexity) 				{ drawSphere(r, g, b, complexity, 0.5); }
	public static void drawSphere(float r, float g, float b, double radius ) 				{ drawSphere(r, g, b, 2, radius); }
	public static void drawSphere(float r, float g, float b, int complexity, double radius) { drawSphere(r, g, b, complexity, radius, true); }
	
	public static void drawSphere(float r, float g, float b, int complexity, double radius, boolean useColor) {
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
			if(useColor)
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
