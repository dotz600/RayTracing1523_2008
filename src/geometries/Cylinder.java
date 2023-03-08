package geometries;
import primitives.Ray;


/**
 * this class represent cylinder in space
 * contain radius, ray & height
 */
public class Cylinder extends Tube{

    /** height of the Cylinder */
    private double height;

    /**
     * constructor with parameters
     * using super constructor
     * @param @ray
     * @param @rad
     * @param @h
     */
    public Cylinder(Ray ray, double rad, double h){
        super(ray, rad);
        height = h;
    }

    public double getHeight() {
        return height;
    }
}
