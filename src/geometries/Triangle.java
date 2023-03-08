package geometries;

import primitives.Point;

/**
 * this class represent a triangle in space
 * by geometry with 3 point
 */
public class Triangle extends Polygon {

    /**
     * constructor with 3 point of the triangle, using super constructor
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
