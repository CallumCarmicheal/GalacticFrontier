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
 * The Vector3 class represents a 3 dimension vector of doubles.
 * 
 * @author ags
 */
public class Vector3 extends Tuple3
{
    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Vector3 ()
    {
        super(0, 0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The vector to copy.
     */
    public Vector3 (Tuple3 newTuple)
    {
        super(newTuple.x, newTuple.y, newTuple.z);
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
    public Vector3 (double newX, double newY, double newZ)
    {
        super(newX, newY, newZ);
    }


    /**
     * Sets this vector to the cross product of op1 and op2
     * 
     * @param op1
     * @param op2
     */
    public void cross (Vector3 op1, Vector3 op2)
    {
        this.x = op1.y * op2.z - op1.z * op2.y;
        this.y = op1.z * op2.x - op1.x * op2.z;
        this.z = op1.x * op2.y - op1.y * op2.x;
    }


    /**
     * Returns the dot product of this Vector3 object and the parameter Vector3.
     * 
     * @param rhs The right hand operand.
     * @return The dot product of this Vector3 object and the parameter Vector3.
     */
    public double dot (Vector3 rhs)
    {
        return x * rhs.x + y * rhs.y + z * rhs.z;
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
     * @return the length squared of this vector3
     */
    public double lengthSquared ()
    {
        return x * x + y * y + z * z;
    }


    /**
     * This method will normalize this Vector3 so that its length is 1.0. If the
     * length of the Vector3 is 0, no action is taken.
     */
    public void normalize ()
    {
        double dist = length();
        if (dist != 0)
        {
            x /= dist;
            y /= dist;
            z /= dist;
        }
    }


    /**
     * Add a Vector3 to this Vector3
     * 
     * @param vector the Vector3 to add
     */
    public void add (Vector3 vector)
    {
        add(this, vector);
    }


    /**
     * Add the values of Vector3 v1 and Vector3 v2 and store the sum in this
     * Vector3.
     * 
     * @param v1 the first operand
     * @param v2 the second operand
     */
    public void add (Vector3 v1, Vector3 v2)
    {
        this.x = v1.x + v2.x;
        this.y = v1.y + v2.y;
        this.z = v1.z + v2.z;
    }


    /**
     * Subtract a Vector3 from this Vector3
     * 
     * @param vector the Tuple3 to subtract
     */
    public void sub (Vector3 vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
    }


    /**
     * Subtract one Point3 from another Point3 and set as this Vector
     * 
     * @param p1 the first operand
     * @param p2 the second operand
     */
    public void sub (Point3 p1, Point3 p2)
    {
        this.x = p1.x - p2.x;
        this.y = p1.y - p2.y;
        this.z = p1.z - p2.z;
    }


    /**
     * Add a scalar multiple of a Vector3 to this Vector3
     * 
     * @param scale the scale factor
     * @param vector the vector to scale add
     */
    public void scaleAdd (double scale, Tuple3 vector)
    {
        this.x += scale * vector.x;
        this.y += scale * vector.y;
        this.z += scale * vector.z;
    }
}
