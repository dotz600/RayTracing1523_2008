package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static primitives.Util.alignZero;


/**
 * this class represent sphere
 * contain radius and point
 */
public class Sphere extends RadialGeometry {

    /** middle point inside sphere */
    private final Point center;

    /**
     * constructor with parameters
     * @param p center point
     * @param r radius
     */
    public Sphere(Point p, double r) {

        super(r);
        center = p;
    }

    public Point getCenter() {

        return center;
    }

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

    if (ray.getP0().equals(center)) {
        return List.of(center.add(ray.getDir().scale(radius)));
    }
    Point point = ray.getP0();
    Vector dir_vector = ray.getDir();
    Vector p0_o = center.subtract(point);
    double t_m = alignZero(dir_vector.dotProduct(p0_o));
    double d = alignZero(Math.sqrt(p0_o.dotProduct(p0_o) - t_m*t_m));

    if (d >= radius)//there are no intersections
        return null;

    double t_h = alignZero(Math.sqrt(radius*radius - d*d));
    double t_1 = alignZero(t_m + t_h);
    double t_2 = alignZero(t_m - t_h);

    if (t_1 <= 0 && t_2 <= 0)
        return null;

    if (t_1 > 0 && t_2 <= 0)
        return List.of(ray.getPoint(t_1));

    if (t_1 <= 0 && t_2 > 0)
        return List.of(ray.getPoint(t_2));

    if (t_1 > 0 && t_2 > 0)
        return List.of(ray.getPoint(t_1), ray.getPoint(t_2));

    return null;

}

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (ray.getP0().equals(center)) {
            return List.of(new GeoPoint(this,center.add(ray.getDir().scale(radius))));
        }
        Point point = ray.getP0();
        Vector dir_vector = ray.getDir();
        Vector p0_o = center.subtract(point);
        double t_m = alignZero(dir_vector.dotProduct(p0_o));
        double d = alignZero(Math.sqrt(p0_o.dotProduct(p0_o) - t_m*t_m));

        if (d >= radius)//there are no intersections
            return null;

        double t_h = alignZero(Math.sqrt(radius*radius - d*d));
        double t_1 = alignZero(t_m + t_h);
        double t_2 = alignZero(t_m - t_h);

        if (t_1 <= 0 && t_2 <= 0)
            return null;

        if (t_1 > 0 && t_2 <= 0)
            return List.of(new GeoPoint(this,ray.getPoint(t_1)));

        if (t_1 <= 0 && t_2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t_2)));

        if (t_1 > 0 && t_2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t_1)), new GeoPoint(this, ray.getPoint(t_2)));

        return null;
    }

    /** <li>For now assume that
     *  the point is on sphere,
     *  without making sure<li/>
     *  "n = (p - O).normalize()"
     */
    @Override
    public Vector getNormal(Point p) {

        return p.subtract(center).normalize();
    }
}
