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
 * The Vector2 class represents a 2 dimension vector of doubles.
 * 
 * @author ags
 */
public class Vector2 extends Tuple2
{
    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Vector2 ()
    {
        super(0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The vector to copy.
     */
    public Vector2 (Tuple2 newTuple)
    {
        super(newTuple.x, newTuple.y);
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
    public Vector2 (double newX, double newY)
    {
        super(newX, newY);
    }


    /**
     * Returns the dot product of this Vector2 object and the parameter Vector2.
     * 
     * @param rhs The right hand operand.
     * @return The dot product of this Vector2 object and the parameter Vector2.
     */
    public double dot (Vector2 rhs)
    {
        return x * rhs.x + y * rhs.y;
    }


    /**
     * Returns the length of this vector.
     * 
     * @return The length of this vector.
     */
    public double length ()
    {
        return Math.sqrt(lengthSquared());
    }


    /**
     * Returns the length squared of this vector. Very useful if only comparison
     * of lengths is needed since it saves the square root.
     * 
     * @return the length squared of this Vector2
     */
    public double lengthSquared ()
    {
        return x * x + y * y;
    }


    /**
     * This method will normalize this Vector2 so that its length is 1.0. If the
     * length of the Vector2 is 0, no action is taken.
     */
    public void normalize ()
    {
        double dist = length();
        if (dist != 0)
        {
            x /= dist;
            y /= dist;
        }
    }


    /**
     * Add a Vector2 to this Vector2
     * 
     * @param vector the Vector2 to add
     */
    public void add (Vector2 vector)
    {
        add(this, vector);
    }


    /**
     * Add the values of Vector2 v1 and Vector2 v2 and store the sum in this
     * Vector2.
     * 
     * @param v1 the first operand
     * @param v2 the second operand
     */
    public void add (Vector2 v1, Vector2 v2)
    {
        this.x = v1.x + v2.x;
        this.y = v1.y + v2.y;
    }


    /**
     * Substract a Vector2 from this Vector2
     * 
     * @param vector the Tuple2 to subtract
     */
    public void sub (Vector2 vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
    }


    /**
     * Subtract one Point2 from another Point2 and set as this Vector
     * 
     * @param p1 the first operand
     * @param p2 the second operand
     */
    public void sub (Point2 p1, Point2 p2)
    {
        this.x = p1.x - p2.x;
        this.y = p1.y - p2.y;
    }


    /**
     * Add a scalar multiple of a Vector2 to this Vector2
     * 
     * @param scale the scale factor
     * @param vector the vector to scale add
     */
    public void scaleAdd (double scale, Tuple2 vector)
    {
        this.x += scale * vector.x;
        this.y += scale * vector.y;
    }
}
