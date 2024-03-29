package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * this class represent plane in space,
 * implements Geometry interface
 */
public class Plane extends Geometry {

    /**
     * point in plane
     */
    private final Point q0;

    /**
     * vertical vector to plane
     */
    private final Vector normal;

    /**
     * constructor with 3 point
     * q0 can be any point from the three
     * creates a normal vector by the formula:
     * n = (p1 - p2 X p1 - p3).normalize()
     *
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Plane(Point p1, Point p2, Point p3) {

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector v = p1.subtract(p2);
        Vector u = p1.subtract(p3);

        normal = v.crossProduct(u).normalize();
        q0 = p1;
    }

    /**
     * constructor with normal and point
     *
     * @param p point in th plane
     * @param v vector normal - normalize it
     */
    public Plane(Point p, Vector v) {

        q0 = p;
        normal = v.normalize();
    }

    /**
     * getter - normal vector
     * @return normal vector
     */
    public Vector getNormal() {

        return normal;
    }

    /**
     * @param p point
     * @return normal vector to the plane
     */
    @Override
    public Vector getNormal(Point p) {

        return normal;
    }

    /**
     * getter - point in plane
     * @return point in plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        if(q0.equals(ray.getP0()))
            return null;

        Vector q0_p = q0.subtract(ray.getP0());
        double n_q0p = alignZero(normal.dotProduct(q0_p));
        double n_dir =  alignZero(normal.dotProduct(ray.getDir()));

        // direction and (q0 - p0) is orthogonal to the normal -
        // the ray is parallel to the plane and inside it.
        if (isZero(n_dir) && n_q0p == 0)
            throw new IllegalArgumentException("The ray is parallel to the plane - infinite intersections");

        // normal * direction = 0 - the ray is parallel to the plane but not in the plane
        if(isZero(n_dir))
            return null;

        double t = alignZero(n_q0p / n_dir);
        // If t is negative, the intersection point is behind the ray origin
        if (t <= 0)
            return null;

        // compute the intersection point and add it to the list of intersections
        return List.of(ray.getPoint(t));
    }
    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if(q0.equals(ray.getP0()))
            return null;

        Vector q0_p = q0.subtract(ray.getP0());
        double n_q0p = alignZero(normal.dotProduct(q0_p));
        double n_dir =  alignZero(normal.dotProduct(ray.getDir()));

        // direction and (q0 - p0) is orthogonal to the normal -
        // the ray is parallel to the plane and inside it.
        if (isZero(n_dir) && n_q0p == 0)
            throw new IllegalArgumentException("The ray is parallel to the plane - infinite intersections");

        // normal * direction = 0 - the ray is parallel to the plane but not in the plane
        if(isZero(n_dir))
            return null;

        double t = alignZero(n_q0p / n_dir);
        // If t is negative, the intersection point is behind the ray origin
        if (t <= 0)
            return null;

        // compute the intersection point and add it to the list of intersections
        return List.of(new GeoPoint(this,ray.getPoint(t)));
    }

}
