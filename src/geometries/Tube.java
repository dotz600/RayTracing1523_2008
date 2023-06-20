package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * represent tube in space
 * contain radius and ray
 */
public class Tube extends RadialGeometry {

    /** ray of the tube */
    protected Ray axisRay;

    /**
     * constructor with parameters
     * @param ray ray of the tube
     * @param rad radius
     */
    public Tube(Ray ray, double rad) {
        super(rad);
        axisRay = ray;
    }

    /**
     * assume p is on the tube
     * p0 + ray * (p - p0) * ray.
     * if p is in front of the ray return (p0-p).normalize
     * @param p point
     * @return Vector normal at point p
     */
    @Override
    public Vector getNormal(Point p) {

        Point p0 = this.axisRay.getP0();
        Vector v = this.axisRay.getDir();

        if(p.equals(p0))
            return p.subtract(p0).normalize();

        Vector p_p0 = p.subtract(this.axisRay.getP0());
        if(p_p0.crossProduct(v).length() == 0)
            throw new IllegalArgumentException("p cant be on the ray");

        double t = p_p0.dotProduct(this.axisRay.getDir());
        if(isZero(t))
            // p is in-front of p0
            return p.subtract(p0).normalize();

        Point o = axisRay.getPoint(t);

        return p.subtract(o).normalize();
    }


    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }


    /**
     * Gets a ray and returns intersection GeoPoints between the ray and the geometry.
     * @param ray that possibly intersect the geometry
     * @return List of GeoPoints if any. else NULL
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (Util.isZero(dirV))
                return List.of(new Intersectable.GeoPoint(this, ray.getPoint(radius)));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;


            return List.of(new Intersectable.GeoPoint(this, ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v))).lengthSquared()))));


        }
        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (Util.isZero(a)) {
            if (Util.isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new Intersectable.GeoPoint(this,ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = Util.alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = Util.alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = Util.alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<GeoPoint> points = new LinkedList<>();
            points.add(new Intersectable.GeoPoint(this,ray.getPoint(t1)));
            points.add(new Intersectable.GeoPoint(this,ray.getPoint(t2)));
            return points;
        }
        else if (t1 > 0) {
            List<GeoPoint> points = new LinkedList<>();
            points.add(new Intersectable.GeoPoint(this,ray.getPoint(t1)));
            return  points;
        }
        else if (t2 > 0) {
            List<GeoPoint> points = new LinkedList<>();
            points.add(new Intersectable.GeoPoint(this,ray.getPoint(t2)));
            return points;
        }
        return null;
    }

    /**
     * get the ray
     * @return ray
     */
    public Ray getAxisRay() {

        return axisRay;
    }
}
