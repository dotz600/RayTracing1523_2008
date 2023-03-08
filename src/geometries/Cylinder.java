package geometries;
import primitives.Ray;


/**
 * this class represent cylinder in space
 * contain radius, ray & height
 */
public class Cylinder extends Tube{

    /** height of the Cylinder */
    private final double height;

    /**
     * constructor with parameters
     * using super constructor
     * @param ray of the cylinder
     * @param rad radius
     * @param h height
     */
    public Cylinder(Ray ray, double rad, double h){
        super(ray, rad);
        height = h;
    }

    public double getHeight() {
        return height;
    }
}
