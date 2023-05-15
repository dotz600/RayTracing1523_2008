package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

public class RayTracerBasic extends RayTracerBase {


    /**
     * constructor with scene parameter
     * @param scene the scene to render
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * get the color of the pixel where the ray intersect with the scene
     * if there is no intersection return the background color
     *
     * @param ray  the ray that intersect with the scene
     * @return Color of the pixel
     */
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersectionsHelper(ray);
        if (intersections == null)
            return scene.getBackground();
        GeoPoint closestGeopoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestGeopoint);
    }

    private Color calcColor(GeoPoint closestPoint) {

        return scene.getAmbientLight().getIntensity().add((closestPoint.geometry.getEmission()));
    }
}
