package com.callumcarmicheal.solar.render;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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


/*
 * (CALLUMC) 
 * I am leaving the camera controls because i cannot figure them out ;(
 * I'll leave it to the community or another dev to help out with, but for now i have given up!
 * [04/07/2015 | 15:03]
 */
public class Camera {

	private Vector3f position;
	private Vector3f rotation;
	private Vector4f renderSettings;
	public boolean ISREADY = false;

	public Vector3f getLocation() {
		return this.position;
	}

	public Vector3f getRotation() {
		return this.rotation;
	}

	public Vector4f getRenderSettings() {
		return this.renderSettings;
	}

	public void setRenderSettings(Vector4f settings) {
		this.renderSettings = settings;
	}

	public void setRenderSettings(float FOV, float ASPECT, float vNEAR,
			float vFAR) {
		setRenderSettings(new Vector4f(FOV, ASPECT, vNEAR, vFAR));
		initProjection();
	}

	public Camera(Vector4f renderSettings) {
		this.renderSettings = renderSettings;

		position = new Vector3f(0.00001f, 0.00001f, 0.00001f);
		rotation = new Vector3f(0.00001f, 0.00001f, 0.00001f);

		initProjection();
	}

	public Camera(float FOV, float ASPECT, float vNEAR, float vFAR) {
		this(new Vector4f(FOV, ASPECT, vNEAR, vFAR));
	}

	private void initProjection() {
		// OLD INIT PARAMS
		// GL11.glShadeModel( GL11.GL_FLAT );
		// GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		// GL11.glClearDepth( 1.0 );
		// GL11.glEnable( GL11.GL_DEPTH_TEST );

		int w = Display.getWidth();
		int h = Display.getHeight();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(renderSettings.FOV, renderSettings.ASPECT,
				renderSettings.NEAR, renderSettings.FAR);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Fix scale-ing of objects and stuff?
		GL11.glScissor(0, 0, w, h);
		GL11.glViewport(0, 0, w, h);

		ISREADY = true;
	}

	public void useCamera() {
		// GL11.glLoadIdentity();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		{
			GL11.glRotatef(rotation.x, 1, 0, 0);
			GL11.glRotatef(rotation.y, 0, 1, 0);
			GL11.glRotatef(rotation.z, 0, 0, 1);
			GL11.glTranslatef(position.x, position.y, position.z);
		}
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// If the cords get messed up (like leak through renders, uncomment this)
		//position.x = 0; position.y = 0; position.z = 0; 
		//rotation.x = 0; rotation.y = 0; rotation.z = 0;
	}

	// moves the camera forward relative to its current rotation (rot.y)
	public void walkForward(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(rotation.y));
		position.z += distance * (float) Math.cos(Math.toRadians(rotation.y));
	}

	// moves the camera backward relative to its current rotation (rot.y)
	public void walkBackwards(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(rotation.y));
		position.z -= distance * (float) Math.cos(Math.toRadians(rotation.y));
	}


	public void moveZ(float amount) {
		position.z += amount * Math.sin(Math.toRadians(rotation.y + 90));
		position.x += amount * Math.cos(Math.toRadians(rotation.y * 90));
	}

	public void moveY(float amount) {
		position.y += amount;
	}

	public void moveX(float amount) {
		position.z += amount * Math.sin(Math.toRadians(rotation.y));
		position.x += amount * Math.cos(Math.toRadians(rotation.y));
	}
	
	public void move(float amount, float direction) {
	    position.z += amount * Math.sin(Math.toRadians(rotation.y + 90 * direction));
	    position.x += amount * Math.cos(Math.toRadians(rotation.y + 90 * direction));
	}
	
	public void rotateZ(float amount) {
		//rotation.x += amount * Math.sin(Math.toRadians(position.y));
		rotation.z += amount * Math.cos(Math.toRadians(position.y));
	}
	
	public void rotateY(float amount) {
		rotation.y += amount;
	}
	
	public void rotateX(float amount) {
		rotation.x += amount;
	}
	
	public void keyboardUpdate(boolean isEvent, boolean KBEventState) {
		float speedMultiplier = 5f;
		float speedDeplier    = 2f;
		
		if (isEvent) { /* PRESS ONCE STUFF */
			if (KBEventState) {
				// Pressed
			} else {
				// Released
			}
		} else { /* HOLDABLE KEYS */
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				// moveZ(0.01f);
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					walkForward(1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					walkForward(0.01f * speedDeplier);
				} else {
					walkForward(0.1f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					walkBackwards(1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					walkBackwards(0.01f * speedDeplier);
				} else {
					walkBackwards(0.1f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveX(-1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					moveX(-0.01f * speedDeplier);
				} else {
					moveX(-0.1f * speedMultiplier);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveX(1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					moveX(0.01f * speedDeplier);
				} else {
					moveX(0.1f * speedMultiplier);
				}
			}

			//if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			//	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			//		walkLeft(10f);
			//	} else {
			//		walkLeft(1f);
			//	}
			//}

			if (Keyboard.isKeyDown(Keyboard.KEY_O)) { 
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateZ(4f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateZ(0.04f * speedMultiplier);
				} else {
					rotateZ(0.4f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_P)) { 
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateZ(-4f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateZ(-0.04f * speedMultiplier);
				} else {
					rotateZ(-0.4f * speedMultiplier);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateX(-1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateX(-0.01f * speedDeplier);
				} else {
					rotateX(-0.1f * speedMultiplier);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateX(1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateX(0.01f * speedDeplier);
				} else {
					rotateX(0.1f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateY(-1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateY(-0.01f * speedDeplier );
				} else {
					rotateY(-0.1f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateY(1f * speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					rotateY(0.01f * speedDeplier);
				} else {
					rotateY(0.1f * speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveY(1* speedMultiplier );
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					moveY(0.01f* speedMultiplier);
				} else {
					moveY(0.1f* speedMultiplier);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveY(-1* speedMultiplier);
				} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					moveY(-0.01f* speedMultiplier);
				} else {
					moveY(-0.1f* speedMultiplier);
				}
			} 
		}
	}

	// I have never used mouse input before, only Keyboard
	public void mouseUpdate() {
		
	}

}
