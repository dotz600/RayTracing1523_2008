package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * this class represent plane in space,
 * implements Geometry interface
 */
public class Plane implements Geometry {

    /**
     * point in plane
     */
    private final Point q0;

    /**
     * vertical vector to plane
     */
    private final Vector normal;

    /**
     * <li>constructor with 3 point</li>
     * <li>q0 can be any point from the three</li>
     * <li>creates a normal vector by the formula: </li>
     * <li>n = (p1 - p2 X p1 - p3).normalize()</li>
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
     * constructor with normal & point
     *
     * @param p point in th plane
     * @param v vector normal - normalize it
     */
    public Plane(Point p, Vector v) {

        q0 = p;
        normal = v.normalize();
    }

    public Vector getNormal() {

        return normal;
    }

    @Override
    public Vector getNormal(Point p) {

        return normal;
    }

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
        double n_q0p = normal.dotProduct(q0_p);
        double n_dir =  normal.dotProduct(ray.getDir());

        // If the dot product is zero, the ray is parallel to the plane
        if (isZero(n_dir))
            return null;

        double t = alignZero(n_q0p / n_dir);
        // If t is negative, the intersection point is behind the ray origin
        if (t <= 0)
            return null;

        // compute the intersection point and add it to the list of intersections
        Vector dist = ray.getDir().scale(t);
        Point p = ray.getP0().add(dist);
        return List.of(p);
    }
}
