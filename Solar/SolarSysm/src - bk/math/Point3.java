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
 * Class for 3d points.
 * 
 * @author arbree
 */
public class Point3 extends Tuple3
{
    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Point3 ()
    {
        super(0, 0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The Point3 to copy.
     */
    public Point3 (Point3 newTuple)
    {
        super(newTuple.x, newTuple.y, newTuple.z);
    }


    /**
     * The explicit constructor. This is the only constructor with any real code
     * in it. Values should be set here, and any variables that need to be
     * calculated should be done here.
     * 
     * @param newX The x value of the new Point3.
     * @param newY The y value of the new Point3.
     * @param newZ The z value of the new Point3.
     */
    public Point3 (double newX, double newY, double newZ)
    {
        super(newX, newY, newZ);
    }


    /**
     * Returns the squared distance from this Point3 to other
     * 
     * @param other another point
     * @return the squared distance from this point to the other point
     */
    public double distanceSquared (Point3 other)
    {
        double dx = (this.x - other.x);
        double dy = (this.y - other.y);
        double dz = (this.z - other.z);
        return dx * dx + dy * dy + dz * dz;
    }


    /**
     * Returns the distance from this Point3 to other
     * 
     * @param other another point
     * @return the distance
     */
    public double distance (Point3 other)
    {
        return Math.sqrt(distanceSquared(other));
    }


    /**
     * Add a Vector3 to this Point3
     * 
     * @param op1 the Vector3 to add
     */
    public void add (Vector3 vector)
    {
        add(this, vector);
    }


    /**
     * Add a Vector3 to a Point3 and store the result in this Point3
     * 
     * @param point the input point
     * @param vector the input vector
     */
    public void add (Point3 point, Vector3 vector)
    {
        this.x = vector.x + point.x;
        this.y = vector.y + point.y;
        this.z = vector.z + point.z;
    }


    /**
     * Subtract a Vector3 to this Point3
     * 
     * @param op1 the Vector3 to substract
     */
    public void sub (Vector3 vector)
    {
        sub(this, vector);
    }


    /**
     * Subtract a Vector3 from a Point3 and store the result in this Point3
     * 
     * @param point the input point
     * @param vector the input vector
     */
    public void sub (Point3 point, Vector3 vector)
    {
        this.x = point.x - vector.x;
        this.y = point.y - vector.y;
        this.z = point.z - vector.z;
    }


    /**
     * Add a scaled multiple of a Vector3 to this Point3
     * 
     * @param scale the input scale
     * @param vector the input vector
     */
    public void scaleAdd (double scale, Vector3 vector)
    {
        this.x += scale * vector.x;
        this.y += scale * vector.y;
        this.z += scale * vector.z;
    }
}
