package primitives;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import geometries.Intersectable.GeoPoint;

/**
 * This class represent ray in space
 * Contains a starting Point & Vector that represent the direction of the ray
 */
public class Ray {
    /** Starting point */
    final Point p0;
    /** Direction */
    final Vector dir;

    /**
     * Constructor with  Point  & Vector
     * @param p Point
     * @param v Vector
     */
    public Ray(Point p, Vector v) {

        p0 = p;
        dir = v.normalize();
    }

    public Point getP0() {
        return p0;
    }

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

    //TODO -- implement this method -- stage 6.4
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {

        //The function RayTracerBasic#traceRay check if intersections is null
        return intersections
                .stream()
                .min(Comparator.comparing(p -> p0.distanceSquared(p.point)))
                .get();
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
