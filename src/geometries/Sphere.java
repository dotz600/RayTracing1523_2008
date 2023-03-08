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
     * @param p
     * @param r
     */
    public Sphere(Point p, double r) {
        super(r);
        Center = p;
    }

    public Point getCenter() {
        return Center;
    }
}
