package geometries;

import primitives.Point;
import primitives.Ray;
import geometries.Intersectable.GeoPoint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a list of geometries
 */
public class Geometries{

    private final List<Intersectable> geometries;

    /**
     * constructor without parameters, creates a new empty list of geometries
     */
    public Geometries() {

        this.geometries = new LinkedList<>();
    }

    /**
     * constructor with parameters, creates a new list of geometries
     *
     * @param geometries array of geometries
     */
    public Geometries(Intersectable... geometries) {

        this.geometries = new LinkedList<>();
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * This method adds the geometries array to the list
     *
     * @param geometries array of geometries
     */
    public void add(Intersectable... geometries) {

        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * This method finds all the intersections of the ray with the geometries in the list
     *
     * @param ray to check if intersect with the geometries and where
     * @return List<Point> - list of all the intersections points
     */
    public List<Point> findIntersections(Ray ray) {

        List<Point> intersections = null;
        for (Intersectable geometry : geometries) {

            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {

                if (intersections == null)
                    intersections = new LinkedList<>();

                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }

    /**
     * This method finds all the intersections of the ray with the geometries in the list
     *
     * @param ray to check if intersect with the geometries and where
     * @return List<GeoPoint> - list of all the intersections points
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;

        for (Intersectable geometry : geometries) {

            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) {

                if (intersections == null)
                    intersections = new LinkedList<>();

                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
