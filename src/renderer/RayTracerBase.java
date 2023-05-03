package renderer;


import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class for ray tracing
 */
public abstract class RayTracerBase {

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
     * @param ray  the ray that intersect with the scene
     * @return Color of the pixel
     */
    public abstract Color traceRay(Ray ray);

}
