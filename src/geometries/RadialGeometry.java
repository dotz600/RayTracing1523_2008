package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * abstract class that represent Radial Geometry
 * sphere, tube & cylinder successor from it
 */
abstract class RadialGeometry implements Geometry {

    /** radius of the geometry */
    protected double radius;

    /**
     * basic constructor with radius
     * @param d
     */
    public RadialGeometry(double d)
    {
        radius = d;
    }

    /**
     * implement get normal from father, for now return null
     * @param p
     * @return
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
