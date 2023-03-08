package primitives;

import java.util.Objects;
import java.util.Vector;

/**
 * this class represent point
 * using Double3 class
 */
public class Point {
    final Double3 xyz;


    /**
     * constructor to initialize xyz with 3 double numbers
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {

        xyz = new Double3(x, y ,z);
    }

    /**
     * constructor to initialize xyz with Double3 Type
     * @param xyz
     */
    Point(Double3 xyz) {

        this.xyz = xyz;
    }

    /**
     * subtract p from this point and return new vector
     * @param p Point
     * @return new vector
     */
    public primitives.Vector subtract(Point p) {

        return new primitives.Vector(this.xyz.subtract(p.xyz));
    }


    /**
     * add vector to point using Double3 add
     * @param v
     * @return new Point
     */
    public Point add(primitives.Vector v) {

        return new Point(xyz.add(v.xyz));
    }

    /**
     * subtract point coordinate from each coordinate and double it with itself
     * @param p Point
     * @return distance double num
     */
    public double distanceSquared(Point p) {

        return (xyz.d1 - p.xyz.d1)*(xyz.d1 - p.xyz.d1) + (xyz.d2 - p.xyz.d2)*(xyz.d2 - p.xyz.d2)+ (xyz.d3 - p.xyz.d3)*(xyz.d3 - p.xyz.d3);
    }

    /**
     * same as distanceSquared only with sqrt
     * @param p
     * @return distance
     */
    public double distance(Point p) {

        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;

        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {

        return "xyz=" + xyz.toString();
    }
}
