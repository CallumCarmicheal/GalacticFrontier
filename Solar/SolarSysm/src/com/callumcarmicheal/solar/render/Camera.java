package com.callumcarmicheal.solar.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.callumcarmicheal.solar.maths.Vector3f;
import com.callumcarmicheal.solar.maths.Vector4f;

//import static org.lwjgl.opengl.GL11.*; 

// Project started at (3 oclock july 1st)
// BRAIN TURNED ON AT 01:10 at 04....

// I DONT NEED TO THAT FREGGING GL11.glBLAH(); CRAP NOW!!!!!!!!!!!!!! 
// THE GREATEST THING KNOWN TO MAN SINCE DUCT TAPE!

// Sidenote to though on GH, this is the point in the stream where i remembered about this (import static) 
// i had been using GL11 EVERYWHERE!

// UPDATE : AT 01:15 I DECIDED I WANT GL11.glBAH(); BACK

public class Camera {

	private Vector3f loc;
	private Vector3f rot;
	private Vector4f renderSettings;
	
	public Vector3f getLocation() { return this.loc; }
	public Vector3f getRotation() { return this.rot; }
	public Vector4f getRenderSettings() { return this.renderSettings; }
	
	public void setRenderSettings(Vector4f settings) { this.renderSettings = settings; }
	public void setRenderSettings(float FOV, float ASPECT, float vNEAR, float vFAR) { setRenderSettings(new Vector4f(FOV, ASPECT, vNEAR, vFAR)); }
	
	public Camera(Vector4f renderSettings) {
		this.renderSettings = renderSettings;
		
		loc = new Vector3f(0, 0, 0);
		rot = new Vector3f(0, 0, 0);
		
		initProjection();
	}
	
	public Camera(float FOV, float ASPECT, float vNEAR, float vFAR) { this(new Vector4f(FOV, ASPECT, vNEAR, vFAR)); }
	
	
	private void initProjection() {
		// OLD INIT PARAMS
		//GL11.glShadeModel( GL11.GL_FLAT );
		//GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		//GL11.glClearDepth( 1.0 );
		//GL11.glEnable( GL11.GL_DEPTH_TEST );
		
		int w = Display.getWidth();
		int h = Display.getHeight();
		
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(
			renderSettings.FOV, 
			renderSettings.ASPECT,
			renderSettings.NEAR,
			renderSettings.FAR
		);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// Fix scale-ing of objects and stuff?
		//GL11.glScissor(0, 0, w, h);
		//GL11.glViewport(0, 0, w, h);
		
		System.out.println("OK CAMERA STARTED?");
	}
	
	public void useCamera() {
		GL11.glLoadIdentity();
		GL11.glRotatef(rot.x, 0, 0, 0);
		GL11.glRotatef(rot.y, 0, 0, 0);
		GL11.glRotatef(rot.z, 0, 0, 0);
		GL11.glTranslatef(loc.x, loc.y, loc.z);
		
		System.out.println("OK I MOVE : " + loc.toString());
	}
	
	public void moveZ(float amount) {
		loc.z += amount * Math.sin(Math.toRadians(rot.y + 90));
		loc.x += amount * Math.cos(Math.toRadians(rot.y * 90));
	}
	
	public void moveX(float amount) {
		loc.z += amount * Math.sin(Math.toRadians(rot.y));
		loc.x += amount * Math.cos(Math.toRadians(rot.y));
	}
	
	public void rotateX(float amount){ rot.x += amount; }
	public void rotateY(float amount){ rot.z += amount; }
	
	public void keyboardUpdate(boolean isEvent, boolean KBEventState) {
		if(isEvent) { /* PRESS ONCE STUFF */
			if(KBEventState) { } else { }
		} else { /* HOLDABLE KEYS */
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				moveZ(1f);
			}
		}
	}
	
	// I have never used mouse input before, only Keyboard
	public void mouseUpdate() {
		int x = Mouse.getX(); // will return the X coordinate on the Display.
		int y = Mouse.getY(); // will return the Y coordinate on the Display.
		
		//System.out.println("MOUSE (" + x + " | " + y + ")");
		
		moveX(x / 10);
		moveZ(y / 10);
	}
	
}
