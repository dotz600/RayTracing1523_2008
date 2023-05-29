package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Testing basic shadows
 * @author Dan */
public class ShadowTests {
    private Intersectable sphere     = new Sphere(new Point(0, 0, -200), 60d)                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Material      trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private Scene         scene      = new Scene.SceneBuilder("Test scene").build();
    private Camera        camera     = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /** Helper function for the tests in this module */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.getGeometries().add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.getLights().add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere and triangle with point light and shade */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("shadowSphereTriangleInitial", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 200));
    }

}
