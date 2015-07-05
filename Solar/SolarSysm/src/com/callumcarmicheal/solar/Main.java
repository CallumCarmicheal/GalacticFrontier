package com.callumcarmicheal.solar;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.callumcarmicheal.solar.objects.IPlanet;
import com.callumcarmicheal.solar.render.Camera;


/*
 * @name SolarSystem Demo
 * @author Callum Carmicheal
 * @date 02/07/2015
 */
public class Main {
	
	public static Main instance;
	
	// Variables
	boolean disposing = false;
	boolean spinMode = true;
	boolean singleStep = false;
	TrueTypeFont renderFont;
	boolean fontLoaded = false;
	boolean renderInfo = true;
	Camera renderCamera;
	boolean grabMouse = false;
	float 
		FOV = 60.0f, 
		zNear = 0.001f, //TODO : fix clipping range (Favour close over far)
		zFar =  8500.0f;
	
	
	// Animation -> Simulation Variables
	float HourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // Set to 0.0f if problem occurs
	float DayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR); // Set to 0.0f if problem occurs
	int NumberOfYear = Calendar.getInstance().get(Calendar.YEAR);;		// Set to currentYear (MANUALLY) if problem occurs
	public float AnimateIncrement = 24.0f; 									// 24 hour per tick
	String simOutput;
	List<IPlanet> simObjects;
	
	// Render using code or Objects
	boolean HardRender = true;
	
	void keyboardListener() {
		
		// Check if our keyboard listener is created and then execute commands from it
		if(Keyboard.isCreated()) {
			// HOLD KEYS
			renderCamera.keyboardUpdate(false, false);
			
			// Fetch all pressed keys (Press once)
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
					
					if(Keyboard.getEventKey() == Keyboard.KEY_T) {
						singleStep = true;
						spinMode = true;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_G) {
						this.HardRender = !this.HardRender;
					}
					
					// Page up
					if(Keyboard.getEventKey() == Keyboard.KEY_PRIOR) {
						// Double the animation time step
						AnimateIncrement *= 2.0f;
					}

					// Page down
					if(Keyboard.getEventKey() == Keyboard.KEY_NEXT) {
						// Halve the animation time step
						AnimateIncrement /= 2.0f;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_M) {
						// Toggle mouse grab
						this.grabMouse = !this.grabMouse;
					}
					
					if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						this.disposing = true;
					}
					
					renderCamera.keyboardUpdate(true, true);
				} else /* KEY RELEASED*/ {
					renderCamera.keyboardUpdate(true, false);
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
	
	void mouseListener() {
	
		// Check if our mouse listener is created and then execute commands from it
		if(Mouse.isCreated()) {
			// Set Grabbed state
			Mouse.setGrabbed(this.grabMouse);
			
			if(grabMouse){
				
				// Now do everything we need to
				renderCamera.mouseUpdate();
				
				Mouse.setCursorPosition(
					(Display.getWidth() / 2),
					(Display.getHeight() / 2)
				);
			}
		} else /* Try to create mouse listener */ {
			try {
				Mouse.create();
			} catch (LWJGLException e) {
				System.err.println("Failed to create LWJGL Mouse Listener");
				e.printStackTrace();
			}
		}
	}
	
	void Animate() {
		
		if(renderCamera.getLocation().y > 5500 || renderCamera.getLocation().y < -5500) {
			if(zNear == 0.001f) {
				zNear =  100f;
				zFar  = 100000f;
				
				onWindowResize(false);
			}
		} else {
			if(zNear != 0.001f) {
				zNear =  0.001f;
				zFar =  8500.0f;
				
				onWindowResize(false);
			}
		}
		
		
		// Clear the rendering output/buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		 
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Util.fixCamera();

		GL11.glScaled(1, 1, 1);
		
        GL11.glPushMatrix();
        	//GL11.glLoadIdentity();
    		renderCamera.useCamera();
    	GL11.glPopMatrix();
    	
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
	        
       		this.simOutput = (	
       			"Creators: CallumC, Bastien Fremondiere" + "\n" + 
           		"Hour    : " + HourOfDay + "\n" + 
           		"Day     : " + DayOfYear + "\n" + 
           		"Year    : " + NumberOfYear + "\n" + 
           		"H Inc   : " + AnimateIncrement + "\n" + 
           		"Spin    : " + spinMode + "\n" + 
           		"H Ren   : " + HardRender + "\n"
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
		//if(HardRender) 
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
		/*/} else { */ if(!HardRender) 
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
        		GL11.glPushMatrix(); {
	        		GL11.glLoadIdentity();
	        		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
	        		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        		GL11.glDisable(GL11.GL_CULL_FACE);
	        		GL11.glDisable(GL11.GL_DEPTH_TEST); 
	        		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	        		GL11.glLoadIdentity();
	
	        		int diff = 15;
	           		renderFont.drawString(10, diff * 0,  "Creators: CallumC, Bastien Fremondiere", Color.red);
	           		renderFont.drawString(10, diff * 1,  "Version : Beta 0.3", Color.yellow);
	           		renderFont.drawString(10, diff * 2,  "LOC     : " + renderCamera.getLocation().toString(), Color.green);
	           		renderFont.drawString(10, diff * 3,  "ROT     : " + renderCamera.getRotation().toString(), Color.green);
	           		renderFont.drawString(10, diff * 4,  "Hour    : " + HourOfDay, Color.cyan);
	           		renderFont.drawString(10, diff * 5,  "Day      : " + DayOfYear, Color.cyan);
	           		renderFont.drawString(10, diff * 6,  "Year     : " + NumberOfYear, Color.cyan);
	           		renderFont.drawString(10, diff * 7,  "H Inc   : " + AnimateIncrement, Color.cyan);
	           		renderFont.drawString(10, diff * 8,  "Spin     : " + spinMode, Color.cyan);
	           		renderFont.drawString(10, diff * 9,  "H Ren   : " + HardRender, Color.cyan);
	           		renderFont.drawString(10, diff * 10, "H Ren   : " + HardRender, Color.cyan);
	           		
	           		// Just leave the matrix mode, it knows where you live (it will blow up the software)
	           		// FINE ILL LEAVE YOU ALONE, JESUS!
	           		GL11.glEnable(GL11.GL_DEPTH_TEST);
	           		GL11.glEnable(GL11.GL_CULL_FACE);
	           		GL11.glMatrixMode(GL11.GL_PROJECTION);
        		} GL11.glPopMatrix();
           		GL11.glMatrixMode(GL11.GL_MODELVIEW);
           		
           		GL11.glDisable(GL11.GL_BLEND);
        	} GL11.glPopMatrix();
        	
        	GL11.glTranslatef(0, 0, -20);
        }  // */
        
        GL11.glScaled (0.5f ,0.5f ,0.5f); 
        
		// LWJGL ALREADY HANDLES OPENGL FLUSH's AND BUFFER SWAPS (flushes ?) ill leave it in to be safe
		GL11.glFlush();
		
		
		// LWJGL IS ALREADY DUBBLE BUFFERED SO WE DONT NEED TO
		// REQUEST ANOTHER BUFFER TO CREATED FOR ANIMATION PURPOSE
	}
	
	// This was the original camera code xD
	void render_VINGARDIUM_LEVIOSA() {}
	
	// Initialise OpenGL's rendering mode
	@Deprecated
	void OpenGLInit() {
		GL11.glShadeModel( GL11.GL_FLAT );
		GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		GL11.glClearDepth( 1.0 );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
	}
	
	// Resize the rendering matrix to wrap around the new WxH
	// THIS SEEMS TO BE BROKEN
	void onWindowResize(boolean createCam) {
		
		int w = Display.getWidth();
		int h = Display.getHeight();
		
		float aspectRatio;
		
		// ??????
		h = (h == 0) ? 1 : h;
		w = (w == 0) ? 1 : w;
		
		// View port uses whole number
		aspectRatio = (float)w/(float)h;
		
		// OLD RENDERING SETTINGS
		/*/ Setup the projection view matrix 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluLookAt( FOV, aspectRatio, zNear, zFar);
		
		// Select the Model view Matrix
		GL11.glMatrixMode (GL11.GL_MODELVIEW);
		
		
		// Fix scale-ing of objects and stuff?
		GL11.glScissor(0, 0, w, h);
		GL11.glViewport(0, 0, w, h); */
		
		if(createCam){
			renderCamera = new Camera(FOV, aspectRatio, zNear, zFar);
		} 
		else {
			if(renderCamera.ISREADY) {
				renderCamera.setRenderSettings(FOV, aspectRatio, zNear, zFar);
			}
		}
	}
	
	public void Init(String[] args) {
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
			Mouse.create();
			
			displayShown = true;
			
			if(displayShown) {
				Display.setTitle("LOADING RESOURCES (Loading Font [res/fonts/constan.ttf])");
				
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
				
				Display.setTitle("LOADING RESOURCES (Loading planets)");
				
				//if(!HardRender) {
					simObjects = new ArrayList<IPlanet>();
					
					simObjects.add( new com.callumcarmicheal.solar.objects.Sun() 	 );
					simObjects.add( new com.callumcarmicheal.solar.objects.Mercury() );
					simObjects.add( new com.callumcarmicheal.solar.objects.Venus() 	 );
					simObjects.add( new com.callumcarmicheal.solar.objects.Earth() 	 );
					simObjects.add( new com.callumcarmicheal.solar.objects.Mars()    );
					simObjects.add( new com.callumcarmicheal.solar.objects.Jupiter() );
				//}
				
				Display.setTitle("LOADING RESOURCES (Setting OPENGL settings)");
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		if(displayShown) {
			// Initialise OpenGL
			{
				onWindowResize(true);
			}
			
			Display.setTitle("Solar System Simulation");
			
			// Start Game loop
			while (!disposing){
				if(!disposing)
					disposing = Display.isCloseRequested();
				
				// Now disposing has been updated, check if we are disposing
				// if not lets do our logic, if not lets skip it (JUST THIS ONCE OKAY!)
				if(!disposing) {
					// Do calculations
					if(Display.wasResized())
						onWindowResize(false);
					
					// Take the Input
					mouseListener();
					keyboardListener();
					
					// Render
					Animate();

					// Check if close was requested and Update Display
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
	
}
