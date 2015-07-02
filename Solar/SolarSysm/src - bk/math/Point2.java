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
 * Class for 2d points.
 * 
 * @author arbree
 */
public class Point2 extends Tuple2
{
    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Point2 ()
    {
        super(0, 0);
    }


    /**
     * Copy constructor. This constructor simply calls the explicit constructor
     * with the necessary fields from the input as parameters.
     * 
     * @param newTuple The Point2 to copy.
     */
    public Point2 (Point2 newTuple)
    {
        super(newTuple.x, newTuple.y);
    }


    /**
     * The explicit constructor. This is the only constructor with any real code
     * in it. Values should be set here, and any variables that need to be
     * calculated should be done here.
     * 
     * @param newX The x value of the new Point2.
     * @param newY The y value of the new Point2.
     */
    public Point2 (double newX, double newY)
    {
        super(newX, newY);
    }


    /**
     * Returns the squared distance from this Point2 to other
     * 
     * @param other another point
     * @return the squared distance from this point to the other point
     */
    public double distanceSquared (Point2 other)
    {
        double dx = (this.x - other.x);
        double dy = (this.y - other.y);
        return dx * dx + dy * dy;
    }


    /**
     * Returns the distance from this Point2 to other
     * 
     * @param other another point
     * @return the distance
     */
    public double distance (Point2 other)
    {
        return Math.sqrt(distanceSquared(other));
    }


    /**
     * Add a Vector3 to this Point2
     * 
     * @param op1 the Vector3 to add
     */
    public void add (Vector2 vector)
    {
        add(this, vector);
    }


    /**
     * Add a Vector2 to a Point2 and store the result in this Point2
     * 
     * @param point the input point
     * @param vector the input vector
     */
    public void add (Point2 point, Vector2 vector)
    {
        this.x = vector.x + point.x;
        this.y = vector.y + point.y;
    }


    /**
     * Subtract a Vector2 to this Point2
     * 
     * @param op1 the Vector2 to substract
     */
    public void sub (Vector2 vector)
    {
        sub(this, vector);
    }


    /**
     * Subtract a Vector3 from a Point2 and store the result in this Point2
     * 
     * @param point the input point
     * @param vector the input vector
     */
    public void sub (Point2 point, Vector2 vector)
    {
        this.x = point.x - vector.x;
        this.y = point.y - vector.y;
    }


    /**
     * Add a scaled multiple of a Vector2 to this Point2
     * 
     * @param scale the input scale
     * @param vector the input vector
     */
    public void scaleAdd (double scale, Vector2 vector)
    {
        this.x += scale * vector.x;
        this.y += scale * vector.y;
    }
}
