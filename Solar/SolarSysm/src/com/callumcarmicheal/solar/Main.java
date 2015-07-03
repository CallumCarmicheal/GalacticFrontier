package com.callumcarmicheal.solar;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.callumcarmicheal.OpenGL.GLUT;
import com.callumcarmicheal.OpenGL.Util;
import com.callumcarmicheal.solar.objects.IPlanet;


/*
 * @name SolarSystem Demo
 * @author Callum Carmicheal
 * @date 02/07/2015
 */
public class Main {

	
	// Variables
	boolean disposing = false;
	boolean spinMode = true;
	boolean singleStep = false;
	TrueTypeFont renderFont;
	boolean fontLoaded = false;
	boolean renderInfo = true;
	
	// Animation -> Simulation Variables
	float HourOfDay = 0.0f;
	float DayOfYear = 0.0f;
	int NumberOfYear = Calendar.getInstance().get(Calendar.YEAR);;
	float AnimateIncrement = 24.0f;
	String simOutput;
	List<IPlanet> simObjects;
	
	// Render using code or Objects
	boolean HardRender = false;
	
	void keyboardListener() {
		
		// Check if our keyboard listener is created and then execute commands from it
		if(Keyboard.isCreated()) {
			// Fetch all pressed keys
			while(Keyboard.next()) {
				// Check if key is pressed
				if(Keyboard.getEventKeyState()) { /* KEY PRESSED */
					
					if(Keyboard.getEventKey() == Keyboard.KEY_R) {
						if ( singleStep ) {			
							// If ending single step mode
							singleStep = false; 
							spinMode = true; // Restart animation
						}
						else {
							spinMode = !spinMode; // Toggle animation on and off.
						}
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_S) {
						singleStep = true;
						spinMode = true;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_1) {
						this.HardRender = !this.HardRender;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						// Double the animation time step
						AnimateIncrement *= 2.0f;
					}

					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						// Halve the animation time step
						AnimateIncrement /= 2.0f;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						this.disposing = true;
					}
					
				} else /* KEY RELEASED*/ {
					
				}
			}

		} else /* Try to create keyboard listener */ {
			try {
				Keyboard.create();
			} catch (LWJGLException e) {
				System.err.println("Failed to create LWJGL Keyboard Listener");
				e.printStackTrace();
			}
		}
		
	}
	
	void Animate() {
		// Clear the rendering output/buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		
		//GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
		//GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
		
		// Update the animation state
		if(spinMode) {
			// Calculate the time 
	        HourOfDay += AnimateIncrement;
	        DayOfYear += AnimateIncrement/24.0;
	        if(DayOfYear > 364) { NumberOfYear++; }
	        
	        HourOfDay = HourOfDay - ((int)(HourOfDay/24))*24;
	        DayOfYear = DayOfYear - ((int)(DayOfYear/365))*365;    
	        
	        // Set simulation output
	        simOutput =  (
	        		"Hour : " + HourOfDay + "\n" + 
	        		"Day  : " + DayOfYear + "\n" + 
	        		"Year : " + NumberOfYear
	        );
		}
		
		// Clear the current matrix (Model View)
		GL11.glLoadIdentity();
		
		// Back off eight units to be able to view from origin
		GL11.glTranslatef( 0.0f, 0.0f, -8.0f );
		
		// Rotate the plane of the elliptic
		// (rotate the model's plane about the x axis by fifteen degrees) 
		GL11.glRotatef( 15.0f, 1.0f, 0.0f, 0.0f );
		
		
		// Hard Render = Render it using raw code no Planet Objects
		// 
		if(HardRender) 
		{
			// Draw the sun -- as yellow, sphere
			GL11.glColor3f( 1.0f, 1.0f, 0.0f );
			GLUT.WireSphere3D( 1, 15, 15 );
		
			// Draw Earth and the Moon
			
			// Earth
			{
				// Draw the Earth
				// First position it around the sun
				//		Use DayOfYear to determine its position
				GL11.glRotatef( (float)(360.0 * (DayOfYear) / 365.0), 0.0f, 1.0f, 0.0f );
				GL11.glTranslatef( 5.0f, 0.0f, 0.0f );
				
				GL11.glPushMatrix();  // Save Matrix State
				{
					// Second, rotate earth on its axis
					//		Once again we will use HourOfDay to determine its rotation
					//GL11.glRotatef( (float)(360.0 * HourOfDay/24.0), 0.0f, 1.0f, 0.0f );
					
					// Third, we draw earth as a Sphere
					GL11.glColor3f( 0.2f, 0.2f, 1.0f );
					GLUT.WireSphere3F( 0.4f, 10, 10 );
					
				} GL11.glPopMatrix(); // Restore Matrix State
			}
			
			// Its earth's sub-domain the Moon!
			{
				// Draw the moon
				// 		Use DayOfYear to control its rotation around earth
				GL11.glRotatef( (float)360.0 * DayOfYear / 365.0f, 0.0f, 1.0f, 0.0f );
				GL11.glTranslatef( 0.7f, 0.0f, 0.0f );
				GL11.glColor3f( 1f, 1f, 1f );
				GLUT.WireSphere3F( 0.1f, 5, 5);
			}
		} else {
			for(IPlanet planet : simObjects) {
				planet.update(HourOfDay, DayOfYear, NumberOfYear);
			}
		}
		
		if (singleStep) {
			spinMode = false;
		}
		
		
		// RENDER THE HOUR AND DAY 
        if(fontLoaded && renderInfo) {
        	//renderFont.drawString(0, 0, "UNICORNS BECAUSE THERE MANLY!")
        	GL11.glPushMatrix(); {
        		
        		GL11.glEnable(GL11.GL_BLEND);
        		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        		
        		GL11.glMatrixMode(GL11.GL_PROJECTION);
        		GL11.glPushMatrix();
        		GL11.glLoadIdentity();
        		GL11.glOrtho(0, 800, 600, 0, 1, -1);
        		GL11.glMatrixMode(GL11.GL_MODELVIEW);
        		GL11.glDisable(GL11.GL_CULL_FACE);
        		GL11.glDisable(GL11.GL_DEPTH_TEST); 
        		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        		GL11.glLoadIdentity();

        		int diff = 15;
           		renderFont.drawString(10, diff * 0, "Creators: CallumC, Bastien Fremondiere", Color.red);
           		renderFont.drawString(10, diff * 1, "Hour    : " + HourOfDay, Color.cyan);
           		renderFont.drawString(10, diff * 2, "Day     : " + DayOfYear, Color.cyan);
           		renderFont.drawString(10, diff * 3, "Year    : " + NumberOfYear, Color.cyan);
           		renderFont.drawString(10, diff * 4, "H Inc   : " + AnimateIncrement, Color.cyan);
           		renderFont.drawString(10, diff * 5, "Spin    : " + spinMode, Color.cyan);
           		renderFont.drawString(10, diff * 6, "H Ren   : " + HardRender, Color.cyan);
           		
           		// for example 
           		GL11.glEnable(GL11.GL_DEPTH_TEST);
           		GL11.glEnable(GL11.GL_CULL_FACE);
           		GL11.glMatrixMode(GL11.GL_PROJECTION);
           		GL11.glPopMatrix();
           		GL11.glMatrixMode(GL11.GL_MODELVIEW);
           		
           		GL11.glDisable(GL11.GL_BLEND);
        	} GL11.glPopMatrix();
        	
        }  //   */
		
		
		// LWJGL ALREADY HANDLES OPENGL FLUSH's AND BUFFER SWAPS (flushes ?)
		GL11.glFlush();
		
		
		// LWJGL IS ALREADY DUBBLE BUFFERED SO WE DONT NEED TO
		// REQUEST ANOTHER BUFFER TO CREATED FOR ANIMATION PURPOSE
	}
	
	
	void render_VINGARDIUM_LEVIOSA() {
		
	}
	
	
	// Initialise OpenGL's rendering mode
	void OpenGLInit() {
		GL11.glShadeModel( GL11.GL_FLAT );
		GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		GL11.glClearDepth( 1.0 );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
	}
	
	// Resize the rendering matrix to wrap around the new WxH
	// THIS SEEMS TO BE BROKEN
	void onWindowResize() {
		int w = Display.getWidth();
		int h = Display.getHeight();
		float FOV = 60.0f, zNear = 1.0f, zFar = 100.0f;
		float aspectRatio;
		
		// ??????
		h = (h == 0) ? 1 : h;
		w = (w == 0) ? 1 : w;
		
		// View port uses whole number
		aspectRatio = (float)w/(float)h;
		
		// Setup the projection view matrix 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective( FOV, aspectRatio, zNear, zFar);
		
		// Select the Model view Matrix
		GL11.glMatrixMode (GL11.GL_MODELVIEW);
		
		
		// Fix scale-ing of objects and stuff?
		GL11.glScissor(0, 0, w, h);
		GL11.glViewport(0, 0, w, h);
	}
	
	public void main(String[] args) {
		boolean displayShown = false;
		
		//1  Create and setup Display
		//2  Initialise OpenGL
		//3  Initialise Keyboard functions
		//4  Initialise resize catch
		//5  Start Animation in Render Loop
		//6  END
		
		try {
			Display.setDisplayMode(new DisplayMode(1024, 800));
			Display.setResizable(true);
			Display.create();
			
			Keyboard.create(); // Create keyboard listener?
			
			displayShown = true;
			
			if(displayShown) {
				
				// load font from a .ttf file
				try {
					InputStream inputStream	= ResourceLoader.getResourceAsStream("res/fonts/constan.ttf");
					Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
					awtFont2 = awtFont2.deriveFont(15f); // set font size
					renderFont = new TrueTypeFont(awtFont2, true); // Anti-A 
					
					fontLoaded = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//if(!HardRender) {
					simObjects = new ArrayList<IPlanet>();
					
					simObjects.add( new com.callumcarmicheal.solar.objects.Sun() );
					simObjects.add( new com.callumcarmicheal.solar.objects.Mercury() );
					simObjects.add( new com.callumcarmicheal.solar.objects.Earth() );
				//}
				
				
				// Initialise OpenGL
				OpenGLInit();
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		if(displayShown) {
			
			// Start Game loop
			while (!disposing){
				// Do calculations
				if(Display.wasResized())
					onWindowResize();
				
				// Take keyboard Input
				keyboardListener();
				
				// Render
				Animate();
				
				// Check if close was requested and Update Display
				Display.update();
				if(!disposing)
					disposing = Display.isCloseRequested();
			}
		} else {
			System.out.println("Failed to init the display output");
			System.err.println("Please try again later");
			System.exit(0);
		}
		
		// End the display instance
		Display.destroy();
	}
	
}
