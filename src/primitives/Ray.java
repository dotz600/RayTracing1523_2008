package primitives;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represent ray in space
 * Contains a starting Point and Vector that represent the direction of the ray
 */
public class Ray {

    private static final double DELTA = 0.1;
    /** Starting point */
    final Point p0;
    /** Direction */
    final Vector dir;

    /**
     * Constructor with  Point and Vector
     * @param p Point
     * @param v Vector
     */
    public Ray(Point p, Vector v) {

        p0 = p;
        dir = v.normalize();
    }

    /**
     * constructor with 3 parameters Point, Vector and normal
     * @param p0 starting point
     * @param dir direction
     * @param normal normal
     */
    public Ray(Point p0, Vector dir, Vector normal) {

        this.dir = dir;
       // make sure the normal and the direction are not orthogonal
        double nv = alignZero(normal.dotProduct(dir));
        // if not orthogonal
        if (!isZero(nv)) {
            Vector moveVector = normal.scale(nv > 0 ? DELTA : -DELTA);
            // move the head of the vector in the right direction
            this.p0 = p0.add(moveVector);
        }
        else {
            this.p0 = p0;
        }
    }

    /**
     * getter - starting point
     * @return starting point
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter - direction
     * @return direction
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * This method return a point on the ray 𝑷 = 𝑷𝟎 + 𝒕∙𝒗
     * this point intersect with geometry
     *
     * @param t the distance from the starting point
     * @return Point
     */
    public Point getPoint(double t) {

        return p0.add(dir.scale(t));
    }

    /**
     * find and return the closet point to head of the ray
     *
     * @param intersections list of point
     * @return the closet point to the head of the ray
     */
    public Point findClosestPoint(List<Point> intersections)
    {
        return intersections == null || intersections.isEmpty() ? null :
                findClosestGeoPoint(
                                    intersections
                                            .stream()
                                            .map(p -> new GeoPoint(null,p))
                                            .toList()
                                    ).point;
    }

    /**
     * find and return the closet point to head of the ray
     * @param intersections list of GeoPoint
     * @return the closet point to the head of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {

        //The function RayTracerBasic#traceRay check if intersections is null
        return intersections.stream()
                .min(Comparator.comparing(p -> p0.distanceSquared(p.point))).
                get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {

        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "point p0=" + p0 +
                ",direction =" + dir +
                '}';
    }
}
