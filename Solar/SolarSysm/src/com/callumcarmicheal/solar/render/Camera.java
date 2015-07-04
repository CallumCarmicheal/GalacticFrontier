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

		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);

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

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		ISREADY = true;
	}

	// Does not work, and i have no clue why ;(
	public void useCamera() {
		// GL11.glLoadIdentity();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		{
			GL11.glRotatef(rotation.x, 1, 0, 0);
			GL11.glRotatef(rotation.y, 0, 1, 0);
			GL11.glRotatef(rotation.z, 0, 0, 1);
			GL11.glTranslatef(position.x, position.y, position.z);

			// System.out.println(
			// "OK I MOVE : \n    " +
			// loc.toString() + "    " + rot.toString() + "\n"
			// );
		}
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// reset the cords so they dont mess with our next rotation call
		position.x = 0;
		position.y = 0;
		position.z = 0;
		rotation.x = 0;
		rotation.y = 0;
		rotation.z = 0;
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

	// strafes the camera left relitive to its current rotation (rot.y)
	public void strafeLeft(float distance) {
		position.x -= distance
				* (float) Math.sin(Math.toRadians(rotation.y - 90));
		position.z += distance
				* (float) Math.cos(Math.toRadians(rotation.y - 90));
	}

	// strafes the camera right relitive to its current rotation (rot.y)
	public void strafeRight(float distance) {
		position.x -= distance
				* (float) Math.sin(Math.toRadians(rotation.y + 90));
		position.z += distance
				* (float) Math.cos(Math.toRadians(rotation.y + 90));
	}

	
	  public void moveZ(float amount) { position.z += amount *
	  Math.sin(Math.toRadians(rotation.y + 90)); position.x += amount *
	  Math.cos(Math.toRadians(rotation.y * 90)); }
	  
	  public void moveY(float amount) {
		  position.y += amount;
	  }
	  
	  public void moveX(float amount) { position.z += amount *
	  Math.sin(Math.toRadians(rotation.y)); position.x += amount *
	  Math.cos(Math.toRadians(rotation.y)); }
	 

	public void rotateX(float amount) {
		rotation.x += amount;
	}

	public void rotateY(float amount) {
		rotation.z += amount;
	}

	public void keyboardUpdate(boolean isEvent, boolean KBEventState) {
		if (isEvent) { /* PRESS ONCE STUFF */
			if (KBEventState) { 
				// Pressed
			} else { 
				// Released
			}
		} else { /* HOLDABLE KEYS */
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				// moveZ(0.01f);
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					walkForward(0.1f);
				} else {
					walkForward(0.01f);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					walkBackwards(0.1f);
				} else {
					walkBackwards(0.01f);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					strafeRight(1f);
				} else {
					strafeRight(0.1f);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					strafeLeft(1f);
				} else {
					strafeLeft(0.1f);
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateX(-4f);
				} else {
					rotateX(-1f);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateX(4f);
				} else {
					rotateX(1f);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateY(-4f);
				} else {
					rotateY(-1f);
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					rotateY(4f);
				} else {
					rotateY(1f);
				}
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveY(-1);
				} else {
					moveY(0.01f);
				}
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					moveY(1);
				} else {
					moveY(0.01f);
				}
			}
		}
	}

	// I have never used mouse input before, only Keyboard
	public void mouseUpdate() {
		int x = (Mouse.getX() - (Display.getWidth() / 2)); // will return the X
															// coordinate on the
															// Display.
		int y = (Mouse.getY() - (Display.getWidth() / 2)); // will return the Y
															// coordinate on the
															// Display.

		// System.out.println("MOUSE (" + x + " | " + y + ")");

		rotateY(x * 0.05f);
		rotateX(x * 0.05f);
	}

}
