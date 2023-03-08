package geometries;

import primitives.Point;


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
}
