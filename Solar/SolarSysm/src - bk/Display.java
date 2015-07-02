import org.lwjgl.util.glu.GLU;

import math.Point3;
import math.Vector3;
import planets.ISpaceObject;
import planets.Planet;
import planets.SolarSystem;
import planets.Sun;

import java.awt.event.KeyEvent;
import java.util.*;


/**
 * Display a simple scene to demonstrate OpenGL.
 */
public class Display extends Scene
{
	// constants
    public static final Point3 DEFAULT_CAMERA_FROM = new Point3(65, 13, 3);
    public static final Point3 DEFAULT_CAMERA_TO = new Point3(0, 0, 0);
    public static final Point3 DEFAULT_CAMERA_UP = new Point3(0, 1, 0);
    
	// animation state
    private Point3 myCamFrom;
    private Point3 myCamTo;
    private Point3 myCamUp;
	private SolarSystem ss;

    /**
     * Create the scene with the given arguments.
     * 
     * For example, the number of teapots to display.
     * 
     * @param args command-line arguments
     */
    public Display (String[] args)
    {
        myCamFrom = new Point3();
        myCamTo = new Point3();
        myCamUp = new Point3();
    	ss = new SolarSystem();
        setDefaultCamera();
    }


    /**
     * @return title for this scene
     */
    public String getTitle ()
    {
        return "A solar system!";
    }


    /**
     * Draw all of the objects to display.
     */
    public void display ()
    {
    	ss.draw();
    }


    /**
     * Set the camera's view of the scene.
     */
    public void setCamera ()
    {        
        GLU.gluLookAt(
        		(float)myCamFrom.x, (float)myCamFrom.y, (float)myCamFrom.z,            // from position
        		(float)myCamTo.x,  	(float)myCamTo.y,  	(float)myCamTo.z,            // to position
        		(float)myCamUp.x,  	(float)myCamUp.y,  	(float)myCamUp.z);
    }


    /**
     * Animate the scene by changing its state slightly.
     * 
     * For example, the angle of rotation of the shapes.
     */
    public void animate ()
    {
    	ss.animate();
        // animate model by spinning it a few degrees each time
//        myAngle += 4;
    }
    

    /**
     * Respond to the press of a key.
     * 
     * @param keyCode Java code representing pressed key
     */
    public void keyPressed (int keyCode)
    {
    	Vector3 viewDir = getViewDir();
    	Vector3 upDir = new Vector3(myCamUp);
    	upDir.normalize();
    	
    	Vector3 rightDir = new Vector3();
    	rightDir.cross(viewDir, upDir);
    	rightDir.normalize();
    	
        switch (keyCode) {
		    case KeyEvent.VK_I: // Zoom In
		    	myCamFrom.sub(viewDir);
		    	break;
		    case KeyEvent.VK_O: // Zoom out;
		    	myCamFrom.add(viewDir);
		    	break;
		    // Camera movement: Current implementation rotates around the spot.
            case KeyEvent.VK_LEFT:
            	myCamTo.add(rightDir);
            	break;
            case KeyEvent.VK_RIGHT:
            	myCamTo.sub(rightDir);
            	break;
            case KeyEvent.VK_UP:
            	myCamTo.add(upDir);
            	break;
            case KeyEvent.VK_DOWN:
            	myCamTo.sub(upDir);
            	break;
        }
    }


    /**
     * Respond to the release of a key.
     * 
     * @param keyCode Java code representing released key
     */
    public void keyReleased (int keyCode)
    {
    	
        switch (keyCode)
        {
            case KeyEvent.VK_H:
                // TODO: hide orbits
            	ss.toggleOrbit(false);
                break;
            case KeyEvent.VK_J:
                // TODO: show orbits
            	ss.toggleOrbit(true);
                break;
            case KeyEvent.VK_R:
            	// TODO: restart simulation
            	setDefaultCamera();
            	ss = new SolarSystem();
            	break;
            // Reset camera view
            case KeyEvent.VK_C:
            	setDefaultCamera();
            	break;
        }
    }


    /**
     * Respond to the press and release of an alphanumeric key.
     * 
     * @param key text representing typed key
     */
    public void keyTyped (int keyCode)
    {
        // by default, do nothing
    }
    
    private Vector3 getViewDir() {
    	Vector3 viewDir = new Vector3();
    	viewDir.sub(myCamFrom, myCamTo);
    	viewDir.normalize();
    	return viewDir;
    }
    
	private void setDefaultCamera() {
		myCamFrom.set(DEFAULT_CAMERA_FROM);
        myCamTo.set(DEFAULT_CAMERA_TO);
        myCamUp.set(DEFAULT_CAMERA_UP);
	}
	
}
