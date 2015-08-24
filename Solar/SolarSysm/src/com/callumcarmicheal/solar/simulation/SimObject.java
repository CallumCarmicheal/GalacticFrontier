package com.callumcarmicheal.solar.simulation;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import com.callumcarmicheal.OpenGL.*;

public class SimObject {
	
	// Identifiers 
	public String 
		UID,
		Name;
	
	// Size
	public float 
		x,
		y,
		realWidth = 0,
		realHeight = 0;
	
	// Simulation
	public float 
		velocity,
		radius,
		degree;
	public boolean 
		moveToTheRight,
		objectIsFixed,
		debugged = false;
	public HashMap 
		orbitChildren = new HashMap();
	public SimObject 
		orbitBase;
	
	// Render
	public TrueTypeFont renderFont;
	
	public SimObject(
			String Name, float X, float Y, 
			float Width, float Height, boolean isFixed) {
		this.Name = Name;
		this.x = X;
		this.y = Y;
		this.realWidth = Width;
		this.realHeight = Height;
		this.objectIsFixed = isFixed;
		
		generateUID();
	}
	
	public SimObject(
			String Name, float X, float Y, 
			float Width, float Height, boolean isFixed, 
			float Radius, float Velocity, boolean moveToTheRight) {
		
		this.Name = Name;
		this.x = X;
		this.y = Y;
		this.realWidth = Width;
		this.realHeight = Height;
		this.objectIsFixed = isFixed;
		this.radius = Radius;
		this.velocity = Velocity;
		this.moveToTheRight = moveToTheRight;
		
		generateUID();
	}
	
	private void generateUID() {
		this.UID = Long.toString(System.currentTimeMillis()) + "$" + Long.toString(Math.round(Math.random()*1200000));
	}
	
	public void onUpdate(SimOptions ops) {
		if(!this.objectIsFixed) {
			for(long i=0; i < ops.simulationTime; i++) {
				degree += velocity;
				while (degree > 360) 
					degree = degree % 360;
			}
			
			x = (orbitBase.x + Math.round(radius * Math.cos(Math.toRadians(degree))));
			y = (orbitBase.y + Math.round(radius * Math.cos(Math.toRadians(degree))));
		}
	}
	
	public void onRender(SimOptions ops, TrueTypeFont renderFont) {
		GL11.glLoadIdentity();
		
		GL11.glPushMatrix(); {
			
			if(this.objectIsFixed) {
				GL11.glTranslatef( x, y, 0f );
			} else {
				GL11.glTranslatef( orbitBase.x - radius, orbitBase.y - radius, 0f );
			}
			
			if((ops.drawOrbit || ops.debugging || debugged) && !objectIsFixed) { 
				GL11.glColor3f(1f, 0f, 0f);
				GLUT.WireSphere3D(radius, 10, 10);
			} 
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GLUT.drawSphere(1f, 0f, 1f, 3, 10f);
			//System.out.print("SimObject(" + Name + UID +  ") -> Render \n");
			
			if((ops.drawRadius || ops.debugging || debugged) && !objectIsFixed) { 
				GL11.glColor3f(0f, 1f, 0f);
				GL11.glBegin(GL11.GL_LINES); {
					GL11.glVertex3f(orbitBase.x, orbitBase.y, 0f);
					GL11.glVertex3f(x, y, 0f);
				} GL11.glEnd();
			} 
			
			if(ops.debugging || debugged) {
				GL11.glColor3f(0f, 1f, 0f);
				
				GL11.glLineWidth(2);
				GL11.glBegin(GL11.GL_LINES); {
					GL11.glVertex3f(x - 100, y, 0f);
					GL11.glVertex3f(x + 100, y, 0f);
					
					GL11.glVertex3f(x, y - 100, 0f);
					GL11.glVertex3f(x, y + 100, 0f);
				} GL11.glEnd();
				
				GL11.glColor3f(1f, 1f, 0f);
				
				GL11.glBegin(GL11.GL_LINE_LOOP); {
					GL11.glVertex3f(x-4, y-4, 0f);
					GL11.glVertex3f(x-4, y-4, 0f);
				} GL11.glEnd();
			}
			
			if(ops.drawName || ops.debugging || debugged) {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				
				GL11.glColor3f(1f, 1f, 0f);
				GL11.glTranslatef( orbitBase.x - radius, orbitBase.y - radius, 0f );
				GL11.glTranslatef( (float) (Name.length() * -1 / 0.5), realHeight / 2 + 3, 0f);
				renderFont.drawString(0, 0, Name);
			}
			
		} GL11.glPopMatrix();
	}
	
	public void addChild(SimObject baseObject) {
		orbitChildren.put(baseObject.UID, baseObject);
	}
	
	public void removeChild(SimObject baseObject) {
		orbitChildren.remove(baseObject.UID);
	}
	
	public void removeChild(String UID) {
		orbitChildren.remove(UID);
	}
}
