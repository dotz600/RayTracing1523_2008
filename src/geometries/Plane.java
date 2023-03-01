package geometries;

import primitives.Vector;

public class Plane implements Geometry {

    primitives.Point q0;
    primitives.Vector normal;

    public Plane(primitives.Point p1,primitives.Point p2,primitives.Point p3) {

        normal = null;
        q0 = p1;
    }

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

}
