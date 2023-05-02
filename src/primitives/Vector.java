package primitives;


import java.util.Objects;

/**
 * This class represent Vector in space
 * Extends Point and extends the actions of Point
 * Vector is kind of Point
 */
public class Vector extends Point {

    /**
     * Constructor 3 with parameters using super constructor with 3 parameters
     * throw IllegalArgumentException if Vector is Zero Vector
     *
     * @param x Coordinate
     * @param y Coordinate
     * @param z Coordinate
     */
    public Vector(double x, double y, double z) {

        super(x, y, z);
        if (Double3.ZERO.equals(super.xyz))
            throw new IllegalArgumentException("Zero Vector exception");

    }

    /**
     * Constructor with Double3 parameters using Point constructor
     * throw IllegalArgumentException if Vector is Zero Vector
     *
     * @param xyz Double3 Coordinate
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (Double3.ZERO.equals(xyz))
            throw new IllegalArgumentException("Zero vector exception");
    }

    /**
     * Addition between two vectors using Double3.add
     *
     * @param v Vector to add
     * @return new Vector
     */
    public Vector add(primitives.Vector v) {

        return new Vector(this.xyz.add(v.xyz));
    }

    /**
     * Multiply each coordinate of the vector by a number using Double3.scale
     *
     * @param num Number to multiply the Vector
     * @return new Vector
     */
    public Vector scale(double num) {

        return new Vector(xyz.scale(num));
    }

    /**
     * X1*X2 + Y1*Y2 + Z1*Z2 --
     * Multiplying each coordinate by the corresponding coordinate
     *
     * @param v Vector to multiply
     * @return double
     */
    public double dotProduct(primitives.Vector v) {

        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    /**
     * Cross product -- [ (a2b3 - a3b2) , (a3b1 - a1b3) , (a1b2 - a2b1) ]
     *
     * @param v Vector to make crossProduct with it
     * @return new Vector that vertical to the two vectors
     */
    public Vector crossProduct(primitives.Vector v) {

        return new Vector(
                this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2,
                this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3,
                this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1
        );
    }

    /**
     * The length of the Vector by square
     *
     * @return double
     */
    public double lengthSquared() {

        return dotProduct(this);
    }

    /**
     * The length of the Vector
     *
     * @return double
     */
    public double length() {

        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize the Vector, each coordinate divide by the length of the Vector
     *
     * @return new normalize Vector
     */
    public Vector normalize() {

        return new Vector(xyz.d1 / length(), xyz.d2 / length(), xyz.d3 / length());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return Objects.equals(xyz, vector.xyz);
    }

    @Override
    public String toString() {
        return "Vector{" +
                super.toString() +
                '}';
    }
}
