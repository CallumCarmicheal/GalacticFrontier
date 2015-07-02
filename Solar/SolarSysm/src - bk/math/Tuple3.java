package math;


/*******************************************************************************
 * GENERAL CONTRACT FOR ALL MATH CLASSES.
 *  -- The destination of every method is assumed to be the object the method
 * was called on. For example:
 * 
 * c.add(a,b) means c = a + b.
 *  -- Whenever one operand of a binary operand is missing, it is assumed to be
 * the object the method was called upon. For example:
 * 
 * c.add(a) means c = c + a.
 * 
 * ********************************************************************************
 * 
 * Base class for 3d tuples (ie. points and vectors).
 * 
 * @author arbree
 */
public class Tuple3
{
    /** The x coordinate of the Tuple3. */
    public double x;
    /** The y coordinate of the Tuple3. */
    public double y;
    /** The z coordinate of the Tuple3. */
    public double z;


    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Tuple3 ()
    {
        this(0, 0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The vector to copy.
     */
    public Tuple3 (Tuple3 newTuple)
    {
        this(newTuple.x, newTuple.y, newTuple.z);
    }


    /**
     * The explicit constructor. This is the only constructor with any real code
     * in it. Values should be set here, and any variables that need to be
     * calculated should be done here.
     * 
     * @param newX The x value of the new vector.
     * @param newY The y value of the new vector.
     * @param newZ The z value of the new vector.
     */
    public Tuple3 (double newX, double newY, double newZ)
    {
        x = newX;
        y = newY;
        z = newZ;
    }


    /**
     * Scale this Tuple3 by op1
     * 
     * @param op1 the scale factor
     */
    public void scale (double op1)
    {
        this.x *= op1;
        this.y *= op1;
        this.z *= op1;
    }


    /**
     * Sets this tuple to have the contents of another tuple. Allows quick
     * conversion between points and vectors.
     * 
     * @param inTuple the input tuple
     */
    public void set (Tuple3 inTuple)
    {
        this.x = inTuple.x;
        this.y = inTuple.y;
        this.z = inTuple.z;
    }


    /**
     * Set the value of this Tuple3 to the three input values
     * 
     * @param inX the new X value
     * @param inY the new Y value
     * @param inZ the new Z value
     */
    public void set (double inX, double inY, double inZ)
    {
        this.x = inX;
        this.y = inY;
        this.z = inZ;
    }


    /**
     * @see Object#toString()
     */
    public String toString ()
    {
        return "[" + x + "," + y + "," + z + "]";
    }
}
