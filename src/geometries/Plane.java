package geometries;

import primitives.Point;


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
     * constructor with 3 point, in the future crate normal with  the points,
     * q0 can be any point from the three
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Plane(primitives.Point p1,primitives.Point p2,primitives.Point p3) {

        normal = null;
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

        return normal.add((primitives.Vector)p);
    }

    public Point getQ0() {
        return q0;
    }

}
