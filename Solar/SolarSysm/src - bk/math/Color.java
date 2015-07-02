package math;


/**
 * This class represents a simple RGB color. The similarities between it and the
 * Tuple3 class are enough to warrant abstraction in a more complicated system,
 * but for simplicity we've kept it separate. There is also a Color class in the
 * java.awt package, but again, we wanted simplicity and full access to the
 * code.
 * 
 * @author ags
 */
public class Color
{
    /** The red component. */
    public double r;

    /** The green component. */
    public double g;

    /** The blue component. */
    public double b;


    /**
     * Default constructor. Produces black.
     */
    public Color ()
    {
        this(0, 0, 0);
    }


    /**
     * Copy constructor.
     * 
     * @param newColor The color to copy.
     */
    public Color (Color newColor)
    {
        this(newColor.r, newColor.g, newColor.b);
    }


    /**
     * The explicit constructor.
     * 
     * @param newR The new red value.
     * @param newG The new green value.
     * @param newB The new blue value.
     */
    public Color (double newR, double newG, double newB)
    {
        r = newR;
        g = newG;
        b = newB;
    }


    /**
     * Sets this color to the value of inColor.
     * 
     * @param inColor the input color
     */
    public void set (Color inColor)
    {
        this.r = inColor.r;
        this.g = inColor.g;
        this.b = inColor.b;
    }


    /**
     * Sets the value of this color to (inR, inG, inB)
     * 
     * @param inR the input red value
     * @param inG the input green value
     * @param inB the input blue value
     */
    public void set (double inR, double inG, double inB)
    {
        this.r = inR;
        this.g = inG;
        this.b = inB;
    }


    /**
     * Sets this color as the product of the pairwise product of this color and
     * rhs.
     * 
     * @param rhs The right hand argument.
     */
    public void scale (Color rhs)
    {
        this.r *= rhs.r;
        this.g *= rhs.g;
        this.b *= rhs.b;
    }


    /**
     * Scales each compoenent of this color by rhs.
     * 
     * @param rhs The scale value to use.
     */
    public void scale (double rhs)
    {
        this.r *= rhs;
        this.g *= rhs;
        this.b *= rhs;
    }


    /**
     * Sets this color to the pairwise sum of this color and ths.
     * 
     * @param rhs the input color
     */
    public void add (Color rhs)
    {
        this.r += rhs.r;
        this.g += rhs.g;
        this.b += rhs.b;
    }


    /**
     * Adds rhs scaled by scale pairwise to this color.
     * 
     * @param scale the scale factor
     * @param rhs the color to be scaled and added
     */
    public void scaleAdd (double scale, Color rhs)
    {
        this.r += scale * rhs.r;
        this.g += scale * rhs.g;
        this.b += scale * rhs.b;
    }


    /**
     * Clamps the each component of this color to between [min,max]
     * 
     * @param min the minimum value
     * @param max the maximum value
     */
    public void clamp (double min, double max)
    {
        r = Math.max(Math.min(r, max), min);
        g = Math.max(Math.min(g, max), min);
        b = Math.max(Math.min(b, max), min);
    }


    /**
     * This function returns an int which represents this color. The standard
     * RGB style bit packing is used and is compatible with
     * java.awt.BufferedImage.TYPE_INT_RGB. (ie - the low 8 bits, 0-7 are for
     * the blue channel, the next 8 are for the green channel, and the next 8
     * are for the red channel). The highest 8 bits are left untouched.
     * 
     * @return An integer representing this color.
     */
    public int toInt ()
    {
        int iR, iG, iB;

        // Here we do the dumb thing and simply clamp then scale.
        // The "+ 0.5" is to achieve a "round to nearest" effect
        // since Java float to int casting simply truncates.
        iR = (int)(255.0 * Math.max(Math.min(r, 1.0), 0.0) + 0.5);
        iG = (int)(255.0 * Math.max(Math.min(g, 1.0), 0.0) + 0.5);
        iB = (int)(255.0 * Math.max(Math.min(b, 1.0), 0.0) + 0.5);

        // Bit packing at its finest
        return (iR << 16) | (iG << 8) | (iB << 0);
    }


    /**
     * @see Object#toString()
     */
    public String toString ()
    {
        return "[" + r + "," + g + "," + b + "]";
    }
}
