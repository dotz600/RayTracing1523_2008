package primitives;

import java.util.Objects;


/**
 * This class represent point
 * using Double3 class
 */
public class Point {
    final Double3 xyz;


    /**
     * Constructor to initialize xyz with 3 double numbers
     * @param x First number value
     * @param y Second number value
     * @param z Third number value
     */
    public Point(double x, double y, double z) {

        xyz = new Double3(x, y ,z);
    }

    /**
     * Constructor to initialize xyz with Double3 type
     * @param xyz Double3
     */
    Point(Double3 xyz) {

        this.xyz = xyz;
    }

    /**
     * Subtract p from this point and return new vector
     * @param p Point
     * @return new Vector
     */
    public Vector subtract(Point p) {

        return new Vector(this.xyz.subtract(p.xyz));
    }


    /**
     * Add Vector to Point using Double3.add
     * @param v vector to add
     * @return new Point
     */
    public Point add(Vector v) {

        return new Point(xyz.add(v.xyz));
    }

    /**
     * d(P1,P2) = (x2-x1)^2 + (y2-y1)^2 + (z2-z1)^2
     * @param p Point
     * @return double -- distance squared
     */
    public double distanceSquared(Point p) {

        return (xyz.d1 - p.xyz.d1)*(xyz.d1 - p.xyz.d1) + (xyz.d2 - p.xyz.d2)*(xyz.d2 - p.xyz.d2)+ (xyz.d3 - p.xyz.d3)*(xyz.d3 - p.xyz.d3);
    }

    /**
     * Calculates the square root of the distance
     * @param p A point to calculate the distance from the current point
     * @return double -- distance
     */
    public double distance(Point p) {

        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {

        return "xyz=" + xyz.toString();
    }

    public double getX() {
        return xyz.d1;
    }

}
