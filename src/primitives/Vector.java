package primitives;


/**
 * this class represent vector in space
 * extend point and extend the action on point
 * vector is kind of point
 */
public class Vector extends Point {

    /**
     * constructor 3 with parameters using super constructor with 3 parameters
     * throw IllegalArgumentException if vector is Zero vector
     * @param  x coordinate
     * @param  y coordinate
     * @param  z coordinate
     */
    public Vector(double x, double y, double z) {

        super(x,y,z);
        if (Double3.ZERO.equals(super.xyz))
            throw new IllegalArgumentException("Zero vector exception");

    }

    /**
     * constructor with Double3 parameters using Point constructor
     * throw IllegalArgumentException if vector is Zero vector
     * @param xyz double3 coordinate
     */
    Vector(Double3 xyz) {

        super(xyz);
        if (Double3.ZERO.equals(xyz))
            throw new IllegalArgumentException("Zero vector exception");
    }
    /**
     * add vector with vector using Double3 add
     * @param v vector to add
     * @return new Vector
     */
    public primitives.Vector add(primitives.Vector v) {

        return new primitives.Vector(this.xyz.add(v.xyz));
    }

    /**
     * multiply each coordinate of the vector with number using Double3 scale
     * @param num number to multiply the vector
     * @return new vector
     */
    public primitives.Vector scale(double num) {

        return new primitives.Vector(xyz.scale(num));
    }

    /**
     * multiply two vector, each coordinate multiply with its twin
     * @param v vector to multiply
     * @return number
     */
    public double dotProduct(primitives.Vector v) {

        return xyz.d1*v.xyz.d1 + xyz.d2*v.xyz.d2 + xyz.d3*v.xyz.d3;
    }

    /**
     * cross multiply the coordinate by X
     * @param v vector to make crossProduct with it
     * @return new vector that vertical to the two vectors
     */
    public primitives.Vector crossProduct(primitives.Vector v) {

        return new primitives.Vector(
                this.xyz.d2*v.xyz.d3 - this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1 - this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2 - this.xyz.d2*v.xyz.d1
        );
    }

    /**
     * the length od the vector by square
     * @return number
     */
    public double lengthSquared() {

        return dotProduct(this);
    }

    /**
     * the length of vector
     * @return number
     */
    public double length() {

        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector, each coordinate divide by the length of the vector
     * @return new normalize vector
     */
    public primitives.Vector normalize() {

        return new primitives.Vector(xyz.d1/length(),xyz.d2/length(),xyz.d3/length());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Vector{" +
                super.toString() +
                '}';
    }
}
