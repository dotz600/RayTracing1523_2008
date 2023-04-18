package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * this class represent a triangle in space
 * by geometry with 3 points
 */
public class Triangle extends Polygon {

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

    /**
     * constructor with 3 points of the triangle, using super constructor
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
