package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * represent tube in space
 * contain radius & ray
 */
public class Tube extends RadialGeometry {

    /** ray of the tube */
    protected primitives.Ray axisRay;

    /**
     * constructor with parameters
     * @param ray ray of the tube
     * @param rad radius
     */
    public Tube(Ray ray, double rad) {
        super(rad);
        axisRay = ray;
    }

    @Override
    public Vector getNormal(Point p) {

        Point O = this.axisRay.getP0()
                .add((this.axisRay.getDir())
                        .scale(p.subtract(this.axisRay.getP0())
                                .dotProduct(this.axisRay.getDir())));
        return p.subtract(O).normalize();
    }

    public Ray getAxisRay() {

        return axisRay;
    }
}
