/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    @Test
    public void polygonTest() {
        Scene          scene1                  = new Scene.SceneBuilder("Test scene1").build();
        Material       material                = new Material()
                .setKd(new Double3(0.2, 0.6, 0.4)
               ).setKs(new Double3(0.2, 0.4, 0.3))
                .setShininess(301);
        Material       material1               = new Material()
                .setKd(0.2)
                .setKs(1)
                .setKR(0.4)
                .setShininess(300);


        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                scene1.getGeometries()
                        .add(new Polygon(
                                new Point(-75 + j * 30, 75 - 30*i, 0),
                                new Point(-75 + j * 30, 45 - 30*i, 0),
                                new Point(-45 + j * 30, 45 - 30*i, 0),
                                new Point(-45 + j * 30, 75 - 30*i, 0))
                                .setMaterial(material)
                                .setEmission((i + j) % 2 == 0 ? new Color(125,61,0) : new Color(96,96,96)));
                scene1.getGeometries()
                        .add(new Sphere(new Point(-60 + j * 30,  (-60 + j * 30) == 30 && (60 - 30*i) == -60 ? - 1000 :60 - 30*i,10), 10d)
                                .setMaterial(material1)
                                .setEmission(new Color(32,32,32)));
            }
        }


        scene1.getLights().add(new PointLight(new Color(800,300,0), new Point(-80,80,60)).setKl(0.001).setKq(0.0002));
        scene1.getLights().add(new PointLight(new Color(800,300,0), new Point(80,-80,60)).setKl(0.001).setKq(0.0002));
        scene1.getLights().add(new PointLight(new Color(500,200,0), new Point(80,-80,60)).setKl(0.001).setKq(0.0002));


        Camera         camera1                 = new Camera(new Point(-380,-380,380),
               new Vector(1,1,-1), new Vector(1,0,1))
                .setVPSize(150, 150).setVPDistance(530);
        ImageWriter imageWriter = new ImageWriter("lightPolygons1", 1000, 1000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }


    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.getGeometries().add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.getLights().add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .build();

        scene.getGeometries().add( //
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));

        scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.15)))
                .build();

        scene.getGeometries().add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.6)));

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(50, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
