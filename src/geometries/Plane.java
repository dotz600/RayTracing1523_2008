package geometries;

import primitives.Point;
import primitives.Vector;


/**
 * this class represent plane in space,
 * implements Geometry interface
 */
public class Plane implements Geometry {

    /** point in plane */
    private final primitives.Point q0;

    /** vertical vector to plane */
    private final primitives.Vector normal;

    /**
     * <li>constructor with 3 point</li>
     * <li>q0 can be any point from the three</li>
     * <li>creates a normal vector by the formula: </li>
     * <li>n = (p1 - p2 X p1 - p3).normalize()</li>
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Plane(primitives.Point p1,primitives.Point p2,primitives.Point p3) {

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        p2.subtract(p3);
        normal = ((p1.subtract(p2)).crossProduct(p1.subtract(p3))).normalize();
        q0 = p1;
    }

    /**
     * constructor with normal & point
     * @param p point in th plane
     * @param v vector normal - normalize it
     */
    public Plane(primitives.Point p,primitives.Vector v) {

        q0 = p;
        normal = v.normalize();
    }

    public primitives.Vector getNormal() {

        return normal;
    }

    @Override
    public primitives.Vector getNormal(primitives.Point p) {

        return p.subtract(normal.scale(-1));
    }

    public Point getQ0() {
        return q0;
    }

}
