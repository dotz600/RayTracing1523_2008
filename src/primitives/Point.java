package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;


/**
 * This class represent point
 * using Double3 class
 */
public class Point {
    public static final Point ZERO = new Point(0,0,0);
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
     * take a center (pixel) point and create a greed of points around it
     * @param RayPerPixel number of rays per pixel
     * @param xStep x step
     * @param yStep y step
     * @param vRight vector right move
     * @param vUp vector up move
     * @return list of points around the center point
     */
    public LinkedList<Point> createGrid(int RayPerPixel, double xStep, double yStep, Vector vRight, Vector vUp){

        LinkedList<Point> points = new LinkedList<>();
        for (int k = -RayPerPixel/ 2; k <= RayPerPixel/2; k++) {
            for (int l = -RayPerPixel/ 2; l <= RayPerPixel/2; l++) {

                Point p_IJ = this;//reset canter point// this == centerPoint
                if (!isZero(xStep) && k != 0)
                    p_IJ = p_IJ.add(vRight.scale(xStep * k));

                if (!isZero(yStep) && l != 0)
                    p_IJ = p_IJ.add(vUp.scale(yStep * l));

                points.add(p_IJ);
            }
        }
        return points;
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
