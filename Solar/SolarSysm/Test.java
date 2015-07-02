import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.callumcarmicheal.OpenGL.GLUT;


public class Test {

	boolean spinMode = true;
	boolean singleStep = false;

	// These three variables control the animation's state and speed.
	float HourOfDay = 0.0f;
	float DayOfYear = 0.0f;
	float AnimateIncrement = 24.0f;  // Time step for animation (hours)
	
	void Key_r(){} 
	void Key_s(){}  
	void Key_up(){} 
	void Key_down(){}
	void ResizeWindow(int w, int h){} 
	void KeyPressFunc( int x, int y ){}
	void SpecialKeyFunc( int Key, int x, int y ){}
	
	/**
	 * Animate() handles the animation and the redrawing of the
	 *		graphics window contents.
	 */
	void Animate(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		if(spinMode) {
			HourOfDay += AnimateIncrement;
	        DayOfYear += AnimateIncrement/24.0;

	        HourOfDay = HourOfDay - ((int)(HourOfDay/24))*24;
	        DayOfYear = DayOfYear - ((int)(DayOfYear/365))*365;
		}
		
		// Clear the current matrix (Modelview)
		GL11.glLoadIdentity();
		
		// Back off eight units to be able to view from the origin.
	    GL11.glTranslatef ( 0.0f, 0.0f, -8.0f );
	    
	    // Rotate the plane of the elliptic
		// (rotate the model's plane about the x axis by fifteen degrees)
	    GL11.glRotatef( 15.0f, 1.0f, 0.0f, 0.0f );
	    
	    // Draw the sun	-- as a yellow, wireframe sphere
	    GL11.glColor3f( 1.0f, 1.0f, 0.0f );			
	    GLUT.WireSphere3D( 1.0, 15, 15 );
	    
	    // Draw the Earth
		// First position it around the sun
		//		Use DayOfYear to determine its position
	    GL11.glRotatef( (float)360.0 *DayOfYear / 365.0f, 0.0f, 1.0f, 0.0f );
	    GL11.glTranslatef( 4.0f, 0.0f, 0.0f );
	    
	    GL11.glPushMatrix();	// Save matrix state
	    {
	   	 	// Second, rotate the earth on its axis.
			//		Use HourOfDay to determine its rotation.
			GL11.glRotatef( (float)(360.0*HourOfDay/24.0), 0.0f, 1.0f, 0.0f );
			// Third, draw the earth as a wireframe sphere.
		    GL11.glColor3f( 0.2f, 0.2f, 1.0f );
		    GLUT.WireSphere3F(0.4f, 10, 10);
	    } GL11.glPopMatrix();	// Restore matrix state
	    
	    // Draw the moon.
		//	Use DayOfYear to control its rotation around the earth
	    GL11.glRotatef( (float)(360.0 * 12.0 * DayOfYear / 365.0f), 0.0f, 1.0f, 0.0f );
	    GL11.glTranslatef( 0.7f, 0.0f, 0.0f );
	    GL11.glColor3f( 0.3f, 0.7f, 0.3f );
	    GLUT.WireSphere3D( 0.1, 5, 5 );
	    
	    GL11.glFlush();
	    
	    //glutSwapBuffers
	    
	    if ( singleStep ) {
			spinMode = false;
		}
	    
	    //glutPostRedisplay
	    
	} 
	
	void OpenGLInit(){
		GL11.glShadeModel( GL11.GL_FLAT );
		GL11.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		GL11.glClearDepth( 1.0 );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
	} 
	
	void ResizeWindow() {
		int w = Display.getWidth();
		int h = Display.getHeight();
		
		float aspectRatio;
		
		GL11.glViewport( 0, 0, w, h );	// View port uses whole window
		aspectRatio = (float)w/(float)h;

		// Set up the projection view matrix (not very well!)
		GL11.glMatrixMode( GL11.GL_PROJECTION );
	    GL11.glLoadIdentity();
	    GLU.gluPerspective( 60.0f, aspectRatio, 1.0f, 30.0f );

		// Select the Modelview matrix
	    GL11.glMatrixMode( GL11.GL_MODELVIEW );
	} 
	
	
	public void main(String[] args) {
		try {
		    Display.setDisplayMode(new DisplayMode(800,600));
		    Display.setResizable(true);
		    Display.create();
		    
		    OpenGLInit();
		    
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
	 
		// init OpenGL here
		while (!Display.isCloseRequested()) {
			if(Display.wasResized())
				ResizeWindow();
			
			// KeyPressFunc(;;);
			// SpecialKeyPressfunc
			
			
		    Display.update();
		}
	 
		Display.destroy();
		
		
	}

}
