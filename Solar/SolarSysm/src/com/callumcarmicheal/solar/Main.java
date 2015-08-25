package com.callumcarmicheal.solar;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.OpenGL.Util;
import com.callumcarmicheal.maths.Vector4f;
import com.callumcarmicheal.solar.render.Camera;
import com.callumcarmicheal.solar.simulation.*;

/*
 * @name Solar System (REWRITE)
 * @author Callum Carmicheal
 * @date 24/08/2015
 * @description A Solar System Simulation
 */
public class Main {

	// Program -> Variables
	public static Main 
		instance;

	// Simulation -> Variables
	SimOptions 
		simulationHandler;

	// Rendering -> Variables
	boolean 
		grabMouse = false, 
		disposing = false, 
		fontLoaded = false,
		isPaused = false,
		isVisible = false,
		updateQuery = false,
		updateQueryRecieved = false,
		updateFinished = false;
	HashMap 
		Stars = new HashMap(), 
		Planets = new HashMap(),
		Satellites = new HashMap();
	TrueTypeFont 
		renderFont;
	Camera 
		renderCamera;
	float
		FOV = 60.0f,
		zNear = 0.001f,
		zFar = 1000.001f;

	
	// LISTENERS -->
	void keyboardListener() {

		// Check if our keyboard listener is created and then execute commands
		// from it
		if (Keyboard.isCreated()) {
			// Hold Keys
			renderCamera.keyboardUpdate(false, false);

			// Fetch all pressed keys (Press once)
			while (Keyboard.next()) {
				// Check if key is pressed
				if (Keyboard.getEventKeyState()) {
					/* KEY PRESSED */ {
						if (Keyboard.getEventKey() == Keyboard.KEY_F1) 
							JOptionPane.showMessageDialog(
								null,
								(Object) 
								"Press 0 to Add a Star \n" +
								"Press 1 to Add a Planet \n" +
								"Press 2 to Add a Satellite \n",
								"Help",
								JOptionPane.PLAIN_MESSAGE);
						
						if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) 
							disposing = true;
						
						if (Keyboard.getEventKey() == Keyboard.KEY_PAUSE) 
							isPaused = !isPaused;
						
						if (Keyboard.getEventKey() == Keyboard.KEY_0)
							addStar();
						
						if (Keyboard.getEventKey() == Keyboard.KEY_1)
							addPlanetOrSatellite(true);
						
						if (Keyboard.getEventKey() == Keyboard.KEY_2)
							addPlanetOrSatellite(false);
						
					}
					renderCamera.keyboardUpdate(true, true);
				} else {
					/* KEY RELEASED */
					renderCamera.keyboardUpdate(true, false);
				}
			}

		} else /* Try to create keyboard listener */{
			try {
				Keyboard.create();
			} catch (LWJGLException e) {
				System.err.println("Failed to create LWJGL Keyboard Listener");
				e.printStackTrace();
			}
		}

	}

	void mouseListener() {

		// Check if our mouse listener is created and then execute commands from
		// it
		if (Mouse.isCreated()) {
			// Set Grabbed state
			//Mouse.setGrabbed(this.grabMouse);
			//if (grabMouse) { }
		} else /* Try to create mouse listener */{
			try {
				Mouse.create();
			} catch (LWJGLException e) {
				System.err.println("Failed to create LWJGL Mouse Listener");
				e.printStackTrace();
			}
		}
	}
	// LISTENERS <--
	
	
	// SIMULATION -->
	void updateSimulationData() { 
		if(!isVisible || isPaused) 
			return;
		
		
	}
	
	void addStar() {
		String starName = JOptionPane.showInputDialog(null, "Enter star's name. *Optional", "Add star to galaxy", JOptionPane.QUESTION_MESSAGE);
		float starX = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter X", "Add star to galaxy", JOptionPane.QUESTION_MESSAGE));
		float starY = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Y", "Add star to galaxy", JOptionPane.QUESTION_MESSAGE));
		float starZ = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Z", "Add star to galaxy", JOptionPane.QUESTION_MESSAGE));
		
		SimObject starObj;
		
		if (starName != null) 
			starObj = new SimObject(starName, starX, starX, starZ, true);
		else 
			starObj = new SimObject("", starX, starX, starZ, true);
		
		protectHashMap(Stars, starObj.UID, starObj);
		JOptionPane.showMessageDialog(null, (Object)"Created Star With Params (N:" + starName + "|X:" + starX + "|Y:" + starY + "|Z:" + starZ + "|true)");
	}
	
	void addPlanetOrSatellite(boolean isPlanet) { 
		String starName = JOptionPane.showInputDialog(null, "Enter object's Name. *Optional", "Add object to star", JOptionPane.QUESTION_MESSAGE);
		int starIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter StarINDEX (TODO: Do something in here ....)", "Add object to star", JOptionPane.QUESTION_MESSAGE));
		
		boolean validRadius = false;
		
		while (validRadius == false) {
			float starX = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter X (Relitive to Home Star)", "Add object to star", JOptionPane.QUESTION_MESSAGE));
			float starY = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Y (Relitive to Home Star)", "Add object to star", JOptionPane.QUESTION_MESSAGE));
			float starZ = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter Z (Relitive to Home Star)", "Add object to star", JOptionPane.QUESTION_MESSAGE));
			
			if(starName != null) {
				String velocityRAW = "0";
				int velocity = 0;
				
				while(velocity == 0) {
					try {
						velocityRAW = JOptionPane.showInputDialog(null, "Select velocity (1-500)", "Adding object to Star", JOptionPane.QUESTION_MESSAGE);
						velocity = Integer.parseInt(velocityRAW);
						
						if( velocity < 1 || velocity > (isPlanet?500:100)) {
							JOptionPane.showMessageDialog(null, "Invalid velocity(" + velocity + ") | please select a velocity (1-500)", "Adding object to Star", JOptionPane.ERROR_MESSAGE);
							velocity = 0;
						}
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "There was a error parsing the value given. (" + velocityRAW + ")", "Error, please try again", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
				
				
				SimObject spaceyRockThingyHomeSTAR = (SimObject) Stars.values().toArray()[0];
				SimObject spaceyRockThingy = new SimObject(
						starName, 
						starX - spaceyRockThingyHomeSTAR.Location.x, 
						starY - spaceyRockThingyHomeSTAR.Location.y, 
						starZ - spaceyRockThingyHomeSTAR.Location.y, 
						false);
				
				
				spaceyRockThingy.orbitBase = connectTo(spaceyRockThingyHomeSTAR, isPlanet);
				spaceyRockThingy.radius = 
					(float) Math.sqrt( 
						Math.abs(spaceyRockThingy.orbitBase.Location.x - spaceyRockThingy.Location.x) * 
						Math.abs(spaceyRockThingy.orbitBase.Location.x - spaceyRockThingy.Location.x) +
						Math.abs(spaceyRockThingy.orbitBase.Location.y - spaceyRockThingy.Location.y) * 
						Math.abs(spaceyRockThingy.orbitBase.Location.y - spaceyRockThingy.Location.y)
					);
				
				if(spaceyRockThingy.radius < 5) {
					JOptionPane.showMessageDialog(null, "", "Adding object to Star", JOptionPane.ERROR_MESSAGE);
				} else {
					validRadius = true;
					
					spaceyRockThingy.degree = (float) Math.toDegrees( 
						Math.asin( 
							Math.abs(
								(spaceyRockThingy.Location.y - spaceyRockThingy.orbitBase.Location.y) /
								spaceyRockThingy.radius
							)
						)
					);
					
					if(spaceyRockThingy.Location.x < spaceyRockThingy.orbitBase.Location.x) {
						if(spaceyRockThingy.Location.y < spaceyRockThingy.orbitBase.Location.y) {
							spaceyRockThingy.degree -= 180;
						} else {
							spaceyRockThingy.degree = 180 - spaceyRockThingy.degree;
						}
					} else {
						if(spaceyRockThingy.Location.y < spaceyRockThingy.orbitBase.Location.y) { 
							spaceyRockThingy.degree *= -1; // Flip the degree.
						}
					}
					
					spaceyRockThingy.velocity = velocity/spaceyRockThingy.radius;
					
					
					System.out.println("Added new object to star + (" + spaceyRockThingy.UID + "|" + spaceyRockThingy.Name + ")");
					
					if(isPlanet) {
						protectHashMap(Planets, spaceyRockThingy.UID, spaceyRockThingy);
					} else {
						protectHashMap(Satellites, spaceyRockThingy.UID, spaceyRockThingy);
					}
				}
			}
		}
	
		
	}
	// SIMULATION <--
	
	
	// HASHMAP -->
	void protectHashMap(HashMap mapList, Object key, Object whatToPlace) {
		//updateQuery = true;
		//while(!updateQueryRecieved) {}
		//updateQueryRecieved = true;
		mapList.put(key, whatToPlace);
		//updateFinished = true;
	}
	
	void protectHashMap() {
		//if(updateQuery) {
		//	updateQuery = false;
		//	updateQueryRecieved = true;
		//	while(!updateFinished) {}
		//	updateFinished = false;
		//}
	}
	
	public SimObject connectTo(SimObject base, boolean isPlanet) {
		Iterator i;
		SimObject master = null;
		HashMap masterSet;
		
		if(isPlanet) {
			masterSet = Stars;
		} else {
			masterSet = Planets;
		}
		
		double Distance = 10000;
		i = masterSet.keySet().iterator();
		
		Object key;
		while (i.hasNext()) {
			key = i.next();
			
			SimObject obj = (SimObject) masterSet.get(key);
			
			double dTemp = 
				Math.sqrt(Math.abs(obj.Location.x - base.Location.x)) * 
				Math.abs( obj.Location.x - base.Location.x ) +
				Math.abs( obj.Location.y - base.Location.y ) *
				Math.abs( obj.Location.y - base.Location.y );
			
			if(dTemp > Distance) {
				master = (SimObject)masterSet.get(key);
				Distance = 
					Math.sqrt( 
						Math.abs(master.Location.x - base.Location.x) * 
						Math.abs(master.Location.x - base.Location.x) +
						Math.abs(master.Location.y - base.Location.y) * 
						Math.abs(master.Location.y - base.Location.y)
					);
			}
		}
		
		
		return master;
	}
	// HASHMAP <--
	
	
	// RENDER -->
	void Render() {
		protectHashMap();
		
		if(!isVisible) 
			return;
		
		// Clear the rendering output/buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Util.fixCamera();
		GL11.glScaled(1, 1, 1);

		GL11.glPushMatrix(); {
			renderCamera.useCamera();
		} GL11.glPopMatrix();

		// Clear the current matrix (Model View)
		GL11.glLoadIdentity();
		
		/* Render Our Planets/System */ {
			
			Iterator ite;
			ite = Stars.values().iterator();
			while(ite.hasNext()) {
				try {
					SimObject theStar = (SimObject) ite.next();
					theStar.onRender(simulationHandler, renderFont);
				} catch (Exception ex) {};
			}
			
			ite = Planets.values().iterator();
			while(ite.hasNext()) {
				try {
					
					SimObject spaceyRockThing = (SimObject)ite.next();
					if(!isPaused) 
						spaceyRockThing.onUpdate(simulationHandler);
					spaceyRockThing.onRender(simulationHandler, renderFont);
				} catch(Exception ex) { }
			}
			
			ite = Satellites.values().iterator();
			while(ite.hasNext()) {
				try {
					SimObject spaceyInterwebConnectorThingy = (SimObject)ite.next();
					if(!isPaused) 
						spaceyInterwebConnectorThingy.onUpdate(simulationHandler);
					spaceyInterwebConnectorThingy.onRender(simulationHandler, renderFont);
				} catch(Exception ex) { }
			}
		}
		
		// Render GUI (Text Overlay)
		if (fontLoaded) {
			GL11.glPushMatrix(); {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glPushMatrix(); {
					GL11.glLoadIdentity();
					GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glDisable(GL11.GL_CULL_FACE);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
					GL11.glLoadIdentity();

					int diff = 15;
					renderFont.drawString(10, diff * 0, "Authors: CallumC, Bastien Fremondiere",										Color.red);
					renderFont.drawString(10, diff * 1, "Version: Who knows, last i remember it was 0.<SOMETHING>", 									Color.yellow);
					renderFont.drawString(10, diff * 2, "LOC    : " + renderCamera.getLocation().toString(), 	Color.green);
					renderFont.drawString(10, diff * 3, "ROT    : " + renderCamera.getRotation().toString(), 	Color.green);
					renderFont.drawString(10, diff * 4, "TIME   : " + simulationHandler.simulationTime, 		Color.cyan);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glEnable(GL11.GL_CULL_FACE);
					GL11.glMatrixMode(GL11.GL_PROJECTION);
				} GL11.glPopMatrix();
				
				GL11.glMatrixMode(GL11.GL_MODELVIEW);

				GL11.glDisable(GL11.GL_BLEND);
			}
			GL11.glPopMatrix();

			GL11.glTranslatef(0, 0, -20);
		}

		GL11.glFlush();

	}

	void onWindowResize(boolean createCam) {

		int w = Display.getWidth();
		int h = Display.getHeight();
		float aspectRatio;
		h = (h == 0) ? 1 : h;
		w = (w == 0) ? 1 : w;
		aspectRatio = (float) w / (float) h;

		if (createCam) 
			renderCamera = new Camera(new Vector4f(FOV, aspectRatio, zNear, zFar));
		else {
			if (renderCamera.ISREADY) 
				renderCamera.setRenderSettings(FOV, aspectRatio, zNear, zFar);
		}
	}
	// RENDER <--
	
	
	public void Init() {
		boolean displayShown = false;
		
		try {
			Display.setDisplayMode(new DisplayMode(1024, 800));
			Display.setResizable(true);
			Display.create();
			Keyboard.create(); // Create keyboard listener
			Mouse.create();

			displayShown = true;

			if (displayShown) {
				Display.setTitle("LOADING RESOURCES (Loading Font [res/fonts/constan.ttf])");

				// Load font from a .TTF file
				try {
					InputStream inputStream = ResourceLoader
							.getResourceAsStream("res/fonts/constan.ttf");
					Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT,
							inputStream);
					awtFont2 = awtFont2.deriveFont(15f); // set font size
					renderFont = new TrueTypeFont(awtFont2, true); // Anti-A

					fontLoaded = true;
				} catch (Exception e) {
					e.printStackTrace();
				}

				Display.setTitle("LOADING RESOURCES ()"); {
					Display.setTitle("LOADING RESOURCES (Loading Sim-Data)");
					simulationHandler = new SimOptions();
				} Display.setTitle("DONE LOADING RESOURCES");
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		if (displayShown) {
			/* Initialise OpenGL */
			onWindowResize(true);

			Display.setTitle("Solar System Simulation");

			// Start Game loop
			while (!disposing) {
				if (!disposing)
					disposing = Display.isCloseRequested();

				// Disposing has been checked, if not lets Render and Start Simulations
				if (!disposing) {
					// Do calculations
					if (Display.wasResized())
						onWindowResize(false);

					// Take the Input
					mouseListener();
					keyboardListener();
					
					// Update our SimData
					updateSimulationData();

					// Render
					Render();

					// Check if close was requested and Update Display
					isVisible = Display.isVisible();
					Display.update();
				}
			}
		} else {
			System.out.println("Failed to init the display output");
			System.err.println("Please try again later");
			System.exit(0);
		}

		// End the display instance
		Display.destroy();
	}

	public Main() {
		this.instance = this;

		Init();
	}
}
