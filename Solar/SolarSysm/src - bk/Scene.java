import java.awt.*;


/**
 * A class that highlights the most useful methods for 
 * you to write to create an OpenGL scene.  You should 
 * subclass this to do your actual work.
 */
public abstract class Scene
{
    /**
     * Get the title of the scene.
     *
     * @return title of scene
     */
    public abstract String getTitle ();


    /**
     * Initialize global OpenGL state.
     *
     * For example, setting lighting or texture parameters
     */
    public void init ()
    {
        // by default, do nothing
    }


    /**
     * Display complete scene.
     * 
     * This is called whenever the contents of the window need to be redrawn.
     * 
     * @param gl   basic interface to OpenGL
     * @param glu  basic interface to GLU
     * @param glut basic interface to GLUT
     */
    public abstract void display ();


    /**
     * Establish camera's view of the scene.
     * 
     * This is called to get the current camera's position.
     * 
     * @param gl   basic interface to OpenGL
     * @param glu  basic interface to GLU
     * @param glut basic interface to GLUT
     */
    public abstract void setCamera ();


    /**
     * Animate scene by making small changes to its state.
     * 
     * For example, changing the absolute position or rotation angle of an object.
     *
     * @param gl   basic interface to OpenGL
     * @param glu  basic interface to GLU
     * @param glut basic interface to GLUT
     */
    public void animate ()
    {
        // by default, do nothing
    }


    /**
     * Respond to the press of a key.
     * 
     * @param keyCode Java code representing pressed key
     */
    public void keyPressed (int keyCode)
    {
        // by default, do nothing
    }


    /**
     * Respond to the release of a key.
     * 
     * @param keyCode Java code representing released key
     */
    public void keyReleased (int keyCode)
    {
        // by default, do nothing
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


    /**
     * Respond to the press and release of the mouse.
     * 
     * @param pt  current position of the mouse
     * @param button  mouse button that was clicked
     */
    public void mouseClicked (Point pt, int button)
    {
        // by default, do nothing        
    }


    /**
     * Respond to the press of the mouse.
     * 
     * @param pt  current position of the mouse
     * @param button  mouse button that was pressed
     */
    public void mousePressed (Point pt, int button)
    {
        // by default, do nothing
    }

    
    /**
     * Respond to the release of the mouse.
     * 
     * @param pt  current position of the mouse
     * @param button  mouse button that was released
     */
    public void mouseReleased (Point pt, int button)
    {
        // by default, do nothing
    }


    /**
     * Respond to the mouse being moved while the button is pressed.
     * 
     * @param pt  current position of the mouse
     * @param button  mouse button that is being held down
     */
    public void mouseDragged (Point pt, int button)
    {
        // by default, do nothing
    }


    /**
     * Respond to the mouse being moved in the canvas.
     * s
     * @param pt  current position of the mouse
     */
    public void mouseMoved (Point pt)
    {
        // by default, do nothing
    }
}
