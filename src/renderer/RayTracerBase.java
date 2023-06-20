package renderer;


import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;

/**
 * abstract class for ray tracing
 */
public abstract class RayTracerBase {

    /**
     * the scene of the ray tracer
     */
    protected Scene scene;

    /**
     * constructor with scene parameter
     * @param scene the scene of the ray tracer
     */
    public RayTracerBase(Scene scene) {

        this.scene = scene;
    }

    /**
     * abstract method for ray tracing
     * return the color of the pixel where the ray intersect with the scene
     * @param rays  rays list that intersect with the scene in the same pixel
     * @return Color of the pixel, the average of the colors of the rays
     */
    public abstract Color traceRays(LinkedList<Ray> rays);

    /**
     * abstract method for ray tracing
     * return the color of the pixel where the ray intersect with the scene
     * @param ray ray that intersect with the scene in the same pixel
     * @return Color of the pixel
     */
    public abstract Color traceRay(Ray ray) ;
}
