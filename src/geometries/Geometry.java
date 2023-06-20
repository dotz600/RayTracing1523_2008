package geometries;

import primitives.*;

/**
 * this interface will serve all the geometries
 */
public abstract class Geometry extends Intersectable{

    /*the emission of the geometry*/
    protected Color emission = Color.BLACK;

    /* the material of the geometry*/
    private Material material = new Material();

    /**
     * chaining method - set the emission of the geometry
     *
     * @param emission the emission of the geometry
     * @return itself (Geometry)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * chaining method - get the emission of the geometry
     *
     * @return itself (Geometry)
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * get the material of the geometry
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * chaining method - set the material of the geometry
     *
     * @param material the material of the geometry
     * @return itself (Geometry)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * return the normal of the geometry
     * @param p point
     * @return normal vector
     */
    public abstract Vector getNormal(Point p);

}
