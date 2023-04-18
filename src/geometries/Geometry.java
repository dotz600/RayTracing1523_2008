package geometries;



/**
 * this interface will serve all the geometries
 */
public interface Geometry extends Intersectable{
    /**
     * return the normal of the geometry
     * @param p point
     * @return normal vector
     */
    primitives.Vector getNormal(primitives.Point p);

}
