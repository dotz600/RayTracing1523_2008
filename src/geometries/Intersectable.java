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
    public abstract List<Point> findIntersections(Ray ray);
    public final List<GeoPoint> findGeoIntersections(Ray ray) {return findGeoIntersectionsHelper(ray);};

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    //TODO -- Documentation here
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor
         * @param geometry
         * @param point
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
