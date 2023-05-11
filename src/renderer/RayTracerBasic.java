package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;


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

        List<Point> intersections = scene.getGeometries().findIntersections(ray);
        if (intersections == null) {
            return scene.getBackground();
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(Point closestPoint) {

        return scene.getAmbientLight().getIntensity();
    }
}
