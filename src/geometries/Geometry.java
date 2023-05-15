package geometries;


import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * this interface will serve all the geometries
 */
public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;


    //TODO -- Documentation here
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    //TODO -- Documentation here
    public Color getEmission() {
        return emission;
    }

    /**
     * return the normal of the geometry
     * @param p point
     * @return normal vector
     */
    public abstract Vector getNormal(Point p);

}
