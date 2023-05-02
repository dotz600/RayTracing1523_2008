package renderer;


import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * TODO -- Documentation here
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * TODO -- Documentation here
     */
    public RayTracerBase(Scene scene) {

        this.scene = scene;
    }

    /**
     * TODO -- Documentation here
     */
    public abstract Color traceRay(Ray ray);

}
