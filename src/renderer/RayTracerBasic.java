package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {


    /**
     * TODO -- Documentation here
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * TODO -- Documentation here
     *
     * @param ray
     * @return color
     */
    @Override
    public Color traceRay(Ray ray) {

        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(Point closestPoint) {

        return scene.ambientLight.getIntensity();
    }
}
