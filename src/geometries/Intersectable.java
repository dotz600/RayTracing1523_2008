package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 * Interface of intersection of light rays with geometric shapes.
 */
public abstract class Intersectable {

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(x -> x.point).toList();
    };

    /**
     * Gets a ray and returns intersection points between the ray and the geometry.
     * @param ray (not NULL)
     * @return List of points if any. else NULL
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {return findGeoIntersectionsHelper(ray);};

    /**
     * implement NVI (Non-Virtual Interface) pattern
     * @param ray to find intersections with
     * @return List of points if any. else NULL
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Class for intersection points between the ray and the geometry.
     */
    public static class GeoPoint {

        /* the geometry  which the point is on*/
        public Geometry geometry;

        /* the point */
        public Point point;

        /**
         * Constructor
         * @param geometry the geometry
         * @param point the point
         */
        public GeoPoint(Geometry geometry,Point point) {

            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
