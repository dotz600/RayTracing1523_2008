package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent a triangle in space
 * by geometry with 3 points
 */
public class Triangle extends Polygon {

    /**
     * constructor with 3 points of the triangle, using super constructor
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        // check if the ray intersect the plane of the triangle
        List<Point> points = plane.findIntersections(ray);
        if (points == null)
            return null;

        // check if the intersection point is inside the triangle
        //create vectors from the intersection point to the vertices
        //(pi - p(i+1) % vertices.size()) X (pi - p0)
        ArrayList<Vector> vectors = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            try {
                Vector v = vertices.get((i + 1) % vertices.size()).subtract(vertices.get(i));
                Vector u = ray.getP0().subtract(vertices.get(i));
                vectors.add(v.crossProduct(u));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        //check all the vector are in the same direction
        //if not - the point is outside the triangle - return null
        //TODO - make normal on time and dont trust getNormal() because the direction is important
        for (int i = 0; i < vectors.size()-1;  i++)
            if (vectors.get(i).dotProduct(this.plane.getNormal()) <= 0)
                return null;

        return points;
    }
}
