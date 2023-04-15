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

    /**
     * assume p is on the tube
     * p0 + ray * (p - p0) * ray.
     * if p is in front of the ray return (p0-p).normalize
     * @param p point
     * @return Vector normal at point p
     */
    @Override
    public Vector getNormal(Point p) {

        try{
            Point O = this.axisRay.getP0()
                    .add((this.axisRay.getDir())
                            .scale(p.subtract(this.axisRay.getP0())
                                    .dotProduct(this.axisRay.getDir())));
            return p.subtract(O).normalize();
        }
        catch (IllegalArgumentException e){
            //p is in front of the ray
            return this.axisRay.getP0().subtract(p).normalize();
        }

    }

    public Ray getAxisRay() {

        return axisRay;
    }
}
