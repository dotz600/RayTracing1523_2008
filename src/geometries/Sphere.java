package geometries;

import primitives.Point;
import primitives.Vector;


/**
 * this class represent sphere
 * contain radius and point
 */
public class Sphere extends RadialGeometry {

    /** middle point inside sphere */
    private Point Center;

    /**
     * constructor with parameters
     * @param p center point
     * @param r radius
     */
    public Sphere(Point p, double r) {

        super(r);
        Center = p;
    }

    public Point getCenter() {

        return Center;
    }

    /** <li>For now assume that
     *  the point is on sphere,
     *  without making sure<li/>
     *  "n = (p - O).normalize()"
     */
    @Override
    public Vector getNormal(Point p) {

        return p.subtract(Center).normalize();
    }
}
