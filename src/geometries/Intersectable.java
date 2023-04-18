package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Interface of intersection of light rays with geometric shapes.
 */
public interface Intersectable {

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    List<Point> findIntersections(Ray ray);
}
