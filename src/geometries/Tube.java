package geometries;

import primitives.Ray;

/**
 * represent tube in space
 * contain radius & ray
 */
public class Tube extends RadialGeometry {

    /** ray of the tube */
    protected primitives.Ray axisRay;

    /**
     * constructor with parameters
     * @param ray
     * @param rad radius
     */
    public Tube(Ray ray, double rad) {
        super(rad);
        axisRay = ray;
    }

    public Ray getAxisRay() {
        return axisRay;
    }
}
