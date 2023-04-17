package geometries;

import primitives.Point;
import primitives.Vector;


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
