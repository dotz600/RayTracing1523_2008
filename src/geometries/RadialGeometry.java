package geometries;

import primitives.Point;
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
     * @param r radius
     */
    public RadialGeometry(double r)
    {
        radius = r;
    }

    /**
     * implement get normal from father, for now return null
     * @param p point
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
