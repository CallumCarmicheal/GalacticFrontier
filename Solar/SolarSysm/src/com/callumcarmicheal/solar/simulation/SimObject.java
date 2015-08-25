package com.callumcarmicheal.solar.simulation;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.callumcarmicheal.OpenGL.*;
import com.callumcarmicheal.maths.Vector3f;

public class SimObject {
	
	// Identifiers 
	public String 
		UID,
		Name;
	
	// Location
	public Vector3f 
		Location;
	
	// Simulation
	public float 
		velocity =0f,
		radius = 0f,
		degree = 0f;
	public boolean 
		moveToTheRight = false,
		objectIsFixed = true,
		debugged = false;
	public HashMap 
		orbitChildren = new HashMap();
	public SimObject 
		orbitBase;
	
	// Render
	public TrueTypeFont renderFont;
	
	public SimObject(
			String Name, float X, float Y, 
			float Z, boolean isFixed) {
		this.Name = Name;
		this.Location = new Vector3f(X, Y, Z);
		this.objectIsFixed = isFixed;
		
		generateUID();
	}
	
	public SimObject(
			String Name, float X, float Y, float Z, 
			boolean isFixed, float Radius, float Velocity, 
			boolean moveToTheRight) {
		this.Name = Name;
		this.Location = new Vector3f(X, Y, Z);
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
			
			Location.x = (orbitBase.Location.x + Math.round(radius * Math.cos(Math.toRadians(degree))));
			Location.x = (orbitBase.Location.y + Math.round(radius * Math.cos(Math.toRadians(degree))));
		}
	}
	
	public void onRender(SimOptions ops, TrueTypeFont renderFont) {
		GL11.glLoadIdentity();
		
		//GL11.glPushMatrix(); {
			//GLUT.drawSphere(1f, 0f, 1f, 10, 1f);
			
			if(this.objectIsFixed) {
				GL11.glTranslatef( Location.x, Location.y, Location.z );
			} else {
				GL11.glTranslatef( orbitBase.Location.x - radius, orbitBase.Location.y - radius, orbitBase.Location.z - radius );
			}
			
			// Draw planet
			if((ops.drawOrbit || ops.debugging || debugged) && objectIsFixed) { 
				GL11.glColor3f(1f, 0f, 0f);
				GLUT.WireSphere3F((objectIsFixed?3:5), 3, 3);
			} 
			
			// Draw radius from parent object
			/*if((ops.drawRadius || ops.debugging || debugged) && !objectIsFixed) { 
				GL11.glPushMatrix(); {
					GL11.glColor3f(0f, 1f, 0f);
					GL11.glBegin(GL11.GL_LINES); {
						GL11.glVertex3f(orbitBase.Location.x, orbitBase.Location.y, orbitBase.Location.z);
						GL11.glVertex3f(Location.x, Location.y, Location.z);
					} GL11.glEnd();
				} GL11.glPopMatrix();
			}*/
			
			// TODO: (FIND OUT WHAT I WAS DOING HERE...) I forgot -.- Really no clue...
			/* if(ops.debugging || debugged) {
				float val = 0.4f;
				
				GL11.glColor3f(0f, 1f, 0f);
				
				GL11.glLineWidth(2);
				GL11.glBegin(GL11.GL_LINES); {
					GL11.glVertex3f(Location.x - val, Location.y, 0f);
					GL11.glVertex3f(Location.x + val, Location.y, 0f);
					
					GL11.glVertex3f(Location.x, Location.y - val, 0f);
					GL11.glVertex3f(Location.x, Location.y + val, 0f);
				} GL11.glEnd();
				
				GL11.glColor3f(1f, 1f, 0f);
				
				GL11.glBegin(GL11.GL_LINE_LOOP); {
					GL11.glVertex3f(Location.x-val, Location.y-val, 0f);
					GL11.glVertex3f(Location.x-val, Location.y-val, 0f);
				} GL11.glEnd();
			} */
			
			// TODO: Render Name
			if(ops.drawName || ops.debugging || debugged) {
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				//GL11.glLoadIdentity();
				
				GL11.glColor3f(1f, 1f, 0f);
				//GL11.glTranslatef( orbitBase.Location.x - radius, orbitBase.Location.y - radius, orbitBase.Location.z - radius );
				GL11.glTranslatef( 0f, 0f, 0f);
				renderFont.drawString(0, 0, Name + "QWEWQEDAWEADW");
			}
			
			//System.out.println("Star (" + Name + "| " + UID + ") Location(" + Location.toString() + ")"); // WOULD HELP IF I SPAWNED A STAR -.-
		//} GL11.glPopMatrix();
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
