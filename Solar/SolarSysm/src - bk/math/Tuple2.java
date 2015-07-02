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
 * Base class for 2d tuples (ie. points and vectors).
 * 
 * @author arbree
 */
public class Tuple2
{
    /** The x coordinate of the Tuple2. */
    public double x;
    /** The y coordinate of the Tuple2. */
    public double y;


    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Tuple2 ()
    {
        this(0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The vector to copy.
     */
    public Tuple2 (Tuple2 newTuple)
    {
        this(newTuple.x, newTuple.y);
    }


    /**
     * The explicit constructor. This is the only constructor with any real code
     * in it. Values should be set here, and any variables that need to be
     * calculated should be done here.
     * 
     * @param newX The x value of the new vector.
     * @param newY The y value of the new vector.
     */
    public Tuple2 (double newX, double newY)
    {
        x = newX;
        y = newY;
    }


    /**
     * Scale this Tuple2 by op1
     * 
     * @param op1 the scale factor
     */
    public void scale (double op1)
    {
        this.x *= op1;
        this.y *= op1;
    }


    /**
     * Sets this tuple to have the contents of another tuple. Allows quick
     * conversion between points and vectors.
     * 
     * @param inTuple the input tuple
     */
    public void set (Tuple2 inTuple)
    {
        this.x = inTuple.x;
        this.y = inTuple.y;
    }


    /**
     * Set the value of this Tuple2 to the three input values
     * 
     * @param inX the new X value
     * @param inY the new Y value
     */
    public void set (double inX, double inY)
    {
        this.x = inX;
        this.y = inY;
    }


    /**
     * @see Object#toString()
     */
    public String toString ()
    {
        return "[" + x + "," + y + "]";
    }
}
