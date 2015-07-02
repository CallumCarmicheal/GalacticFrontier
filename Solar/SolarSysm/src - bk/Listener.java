import java.awt.Point;
import java.awt.event.*;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * This class serves as mediator between your scene code and the boilerplate
 * OpenGL code that needs to be written. It calls your scene methods at the
 * appropriate time.
 * 
 * It may be updated from time to time to add functionality.
 */
public class Listener implements KeyListener {
	// constants
	public static long ONE_SECOND = 1000;

	// user's scene to animate and display
	private Display myScene;

	// animation state
	// private Animator myAnimator;
	private int myFrameCount;
	private long myLastFrameTime;
	private double myFPS;
	private boolean isRunning;
	private boolean showFPS;

	/**
	 * Create this listener with the arguments given on the command-line and the
	 * animation thread.
	 * 
	 * @param args
	 *            command-line arguments
	 * @param animator
	 *            animation thread
	 */
	public Listener(Display scene) {
		myScene = scene;
		isRunning = true;
		myFrameCount = 0;
		myLastFrameTime = System.currentTimeMillis();
		myFPS = 0;
		showFPS = false;
	}

	/**
	 * Get the title of the scene.
	 *
	 * @return title of scene
	 */
	public String getTitle() {
		return myScene.getTitle();
	}

	// //////////////////////////////////////////////////////////
	// GLEventListener methods
	/**
	 * Called once immediately after the OpenGL context is initialized.
	 * 
	 * @see GLEventListener#init(GLAutoDrawable)
	 */
	public void init() {

		// interesting?
		System.err.println("GL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR));
		System.err.println("GL_RENDERER: " + GL11.glGetString(GL11.GL_VENDOR));
		System.err.println("GL_VERSION: " + GL11.glGetString(GL11.GL_VENDOR));
		// System.err.println("GL_CLASS: " + GL11.getClass().getName());

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		// start scene
		myScene.init();
	}

	/**
	 * Called repeatedly to render the OpenGL scene.
	 * 
	 * @see GLEventListener#display(GLAutoDrawable)
	 */
	public void display() {

		// update scene for this time step
		myScene.animate();
		// clear the drawing surface
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// display model
		GL11.glPushMatrix();
		myScene.setCamera();
		myScene.display();
		GL11.glPopMatrix();

		// display frame rate
		computeFPS();
	}

	/**
	 * Called immediately after the component has been resized
	 * 
	 * @see GLEventListener#reshape(GLAutoDrawable, int, int, int, int)
	 */
	public void reshape(int x, int y, int width, int height) {
		// reset camera based on new viewport
		setPerspective(GL11.GL_RENDER, null);
	}

	/**
	 * Called when the display mode or the display device has changed.
	 * 
	 * @see GLEventListener#displayChanged(GLAutoDrawable, boolean, boolean)
	 */
	public void displayChanged(boolean modeChanged, boolean deviceChanged) {
		// not generally used
	}

	/**
	 * Called when the display is closed.
	 * 
	 * @see GLEventListener#dispose(GLAutoDrawable)
	 */
	public void dispose() {
		// not generally used
	}

	// //////////////////////////////////////////////////////////
	// KeyListener methods
	/**
	 * Called when any key is pressed within the canvas.
	 */
	public void keyPressed(KeyEvent e) {
		// pass event onto user's code
		myScene.keyPressed(e.getKeyCode());
	}

	/**
	 * Called when any key is released within the canvas.
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		// toggle animation running
		case KeyEvent.VK_Z:
			showFPS = !showFPS;
			break;
		// toggle animation running
		case KeyEvent.VK_P:
			isRunning = !isRunning;
			// if (isRunning) myAnimator.start();
			// else myAnimator.stop();
			break;
		// quit the program
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_Q:
			// myAnimator.stop();
			System.exit(0);
			break;
		// pass event onto user's code
		default:
			myScene.keyReleased(e.getKeyCode());
		}
	}

	/**
	 * Called when standard alphanumeric keys are pressed and released within
	 * the canvas.
	 */
	public void keyTyped(KeyEvent e) {
		// by default, do nothing
	}

	// //////////////////////////////////////////////////////////
	// helper methods
	/**
	 * Reset perspective matrix based on size of viewport.
	 */
	private void setPerspective(int mode, Point pt) {
		// get info about viewport (x, y, w, h)
		int[] viewport = new int[4];
		IntBuffer buffer = BufferUtils.createIntBuffer(4);
		viewport = buffer.array();

		// GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport, 0);
		GL11.glGetInteger(GL11.GL_VIEWPORT);
		GL11.glGetInteger(GL11.GL_VIEWPORT, buffer);

		// set camera to view viewport area
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// check for selection
		if (mode == GL11.GL_SELECT) {
			// create 5x5 pixel picking region near cursor location
			GLU.gluPickMatrix(pt.x, viewport[3] - pt.y, 5.0f, 5.0f, buffer);
		}
		// view scene in perspective
		GLU.gluPerspective(45.0f, (float) viewport[2] / (float) viewport[3],
				0.1f, 500.0f);
		// prepare to work with model again
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	/*
	 * Compute and print frames per second of animation
	 */
	private double computeFPS() {
		myFrameCount++;
		long currentTime = System.currentTimeMillis();
		if (currentTime - myLastFrameTime > ONE_SECOND) {
			myFPS = myFrameCount * ONE_SECOND
					/ (double) (currentTime - myLastFrameTime);
			myLastFrameTime = currentTime;
			if (showFPS)
				System.out.printf("%3.2f\n", myFPS);
			myFrameCount = 0;
		}

		return myFPS;
	}
}
