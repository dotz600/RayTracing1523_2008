package geometries;

import primitives.Ray;

abstract class RadialGeometry implements Geometry {
    protected double radius;

    public RadialGeometry(double d)
    {
        radius = d;
    }
}
