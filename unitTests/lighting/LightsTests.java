package lighting;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class LightsTests {
    private Material material1 = new Material()
            .setKd(0.2)
            .setKs(1)
            .setShininess(300);
    private  Scene          scene1                  = new Scene.SceneBuilder("Test scene1").build();
    private final Scene          scene2                  = new Scene.SceneBuilder("Test scene1").
            setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();

    private final Camera         camera1                 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(150, 150).setVPDistance(1000);
    private final Camera         camera2                 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200).setVPDistance(1000);

    private static final int     SHININESS               = 301;
    private static final double  KD                      = 0.5;
    private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);

    private static final double  KS                      = 0.5;
    private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);

    private final Material       material                = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
    private final Color          trianglesLightColor     = new Color(800, 500, 250);
    private final Color          sphereLightColor        = new Color(800, 500, 0);
    private final Color          sphereColor             = new Color(BLUE).reduce(2);


    private final Point          sphereCenter            = new Point(0, 0, -50);
    private static final double  SPHERE_RADIUS           = 50d;

    // The triangles' vertices:
    private final Point[]        vertices                =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    private final Point          sphereLightPosition     = new Point(-50, -50, 25);
    private final Point          trianglesLightPosition  = new Point(30, 10, -100);
    private final Vector         trianglesLightDirection = new Vector(-2, -2, -2);

    private final Geometry       sphere                  = new Sphere(sphereCenter, SPHERE_RADIUS)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
    private final Geometry       triangle1               = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    private final Geometry       triangle2               = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    @Test
    public void polygonTest() {

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
                        .add(new Sphere(new Point(-60 + j * 30, 60 - 30*i, 10), 10d)
                                .setMaterial(material1)
                                .setEmission(new Color(32,32,32)));
            }
        }

        scene1.getLights().add(new DirectionalLight(sphereLightColor, new Vector(20, 20, -0.5)));
        scene1.getLights().add(new PointLight(new Color(200,50,0), new Point(80,80,30)).setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightPolygons", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }



    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereMyTest() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new SpotLight(new Color(900,500,500), new Point(50,0,50), new Vector(-1, 0, -1)).setKl(0.001).setKq(0.0001));
        scene1.getLights().add(new DirectionalLight(new Color(90,500,10), new Vector(1, -1, -0.2)));
        scene1.getLights().add(new PointLight(new Color(500,200,100), sphereLightPosition).setKl(0.001).setKq(0.0002));


        ImageWriter imageWriter = new ImageWriter("lightSphereMyTest", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }


    @Test
    public void trianglesMyTest() {

        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new DirectionalLight(new Color(150,400,30), trianglesLightDirection));
        scene2.getLights().add(new SpotLight(new Color(600,200,200), new Point(70,-90,-100), new Vector(-2,-2,-2)).setKl(0.001).setKq(0.0001));
        scene2.getLights().add(new PointLight(new Color(300,100,100), trianglesLightPosition).setKl(0.001).setKq(0.0002));


        ImageWriter imageWriter = new ImageWriter("lightTrianglesMyTest", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of a sphere lighted by a directional light */
    @Test
    public void sphereDirectional() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of a sphere lighted by a point light */
    @Test
    public void spherePoint() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of a sphere lighted by a spotlight */
    @Test
    public void sphereSpot() {
        scene1.getGeometries().add(sphere);
        scene1.getLights().add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                .setKl(0.001).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of two triangles lighted by a directional light */
    @Test
    public void trianglesDirectional() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of two triangles lighted by a point light */
    @Test
    public void trianglesPoint() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of two triangles lighted by a spotlight */
    @Test
    public void trianglesSpot() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.001).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of a sphere lighted by a narrow spotlight */
    @Test
    public void sphereSpotSharp() {
        scene1.getGeometries().add(sphere);
        scene1.getLights()
                .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5)));


        ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /** Produce a picture of two triangles lighted by a narrow spotlight */
    @Test
    public void trianglesSpotSharp() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.getLights().add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

}
