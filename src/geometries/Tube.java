package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * represent tube in space
 * contain radius & ray
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

        Point o = p0.add(v.scale(t));

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

    public Ray getAxisRay() {

        return axisRay;
    }
}
