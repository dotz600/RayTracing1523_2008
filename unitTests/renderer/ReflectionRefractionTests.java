/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Cylinder;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {

    private Scene scene = new Scene.SceneBuilder("Test scene").build();
    final Color BROWN = new Color(102, 51, 0);
    static final Color GOLD = new Color(255, 215, 0);
    static final Color WHITE = new Color(255, 255, 255);
    static final Color BLACK = new Color(0,0,0);

    @Test
    public void itur() {
        Scene          scene1                  = new Scene.SceneBuilder("Test scene1").build();
        Material material = new Material()
                .setKs(0.2)
                .setKd(0.8)
                .setShininess(300);

        Material material1 = new Material()
                .setKs(0.8)
                .setKd(0.1)
                .setKr(1)
                .setShininess(300);

        double startX = -85;
        double endX = -10;
        double startY = -100;
        double endY = -110;
        double startZ = 0;
        double endZ = 10;
        int up = 1;
        int down = -1;
        BuildTriangles(scene1, material);
        BuildCube(scene1, BROWN, material, startX, endX, startY, endY, startZ, endZ);//left down
        addItur(scene1, material1, new Point(-32.5-17,-105,0), down);
        BuildCube(scene1, BROWN, material, startX, endX, -startY, -endY, startZ, endZ);//left up
        addItur(scene1, material1, new Point(-32.5-17,105,0), up);
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, startY, endY, startZ, endZ );//right down
        addItur(scene1, material1, new Point(17.5+17,-105,0), down);
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, -startY, -endY, startZ, endZ );//right up
        addItur(scene1, material1, new Point(17.5+17,105,0), up);
        addGlassAndCubes(scene1);

        startX = -85;
        endX = -80;
        startY = -110;
        endY = 110;
        BuildCube(scene1, BROWN, material, startX, endX, startY, endY, startZ, endZ);//left side
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, startY, endY, startZ, endZ );//right side
        startX = -13;
        endX = -8;
        BuildCube(scene1, BROWN, material, startX, endX, startY, endY, startZ, endZ);//middle left
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, startY, endY, startZ, endZ );//middle right
        startX = -11;
        endX = -8;
        startY = -55;
        endY = -49;
        startZ = 10;
        endZ = 10.2;
        BuildCube(scene1, GOLD, material1, startX, endX, startY, endY, startZ, endZ);//middle left down closer
        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-9,-53,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material),
                new Cylinder(new Ray(new Point(-9,-51,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material)
        );
        BuildCube(scene1, GOLD, material1, -startX -15, -endX-15, startY, endY, startZ, endZ );//middle right down closer
        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-6,-53,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material),
                new Cylinder(new Ray(new Point(-6,-51,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material)
        );
        BuildCube(scene1, GOLD, material1, startX, endX, startY+110, endY+110, startZ, endZ );//middle left up closer
        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-9,57,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material),
                new Cylinder(new Ray(new Point(-9,59,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material)
        );
        BuildCube(scene1, GOLD, material1, -startX -15, -endX-15, startY+110, endY+110, startZ, endZ);//middle right up closer
        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-6,57,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material),
                new Cylinder(new Ray(new Point(-6,59,10.2),new Vector(0,0,1)),0.5,0.5).setMaterial(material)
        );

        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-7.5,-110,10),new Vector(0,1,0)),1,220).setMaterial(material).setEmission(new Color(228,218,199).reduce(2))//center cylinder
        );
        scene1.getGeometries().add(
                new Polygon(
                        new Point(-85,-110,0),
                        new Point(-85,110,0),
                        new Point(70,110,0),
                        new Point(70,-110,0)
                ).setMaterial(material.setKr(0.01))
                        .setEmission(new Color(90,40,0))//brown floor

        );






        scene1.getLights().add(
                new SpotLight(new Color(900,500,500),
                        new Point(-92.5,195,-35), new Vector(1, -1, 1)).setKl(0.001).setKq(0.0001));


        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(150,150,60))
                        .setKl(0.001)
                        .setKq(0.0002));
        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(-150,150,60))
                        .setKl(0.001)
                        .setKq(0.0002));
        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(150,-150,60))
                        .setKl(0.001)
                        .setKq(0.0002));

        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(-150,-150,60))
                        .setKl(0.001)
                        .setKq(0.0002));

        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(-7,0,150))
                        .setKl(0.001)
                        .setKq(0.0002));
        scene1.getLights().add(
                new PointLight(new Color(WHITE.getColor()),
                        new Point(-7,150,100))
                        .setKl(0.001)
                        .setKq(0.0002));



        Camera camera1 = new Camera(
                new Point(145,-545,310),
                new Vector(-0.3,1,-0.55),
                new Vector(0,0.55,1))
                .setVPSize(150, 150)
                .setVPDistance(450);

        ImageWriter imageWriter = new ImageWriter("itur-lights", 2000, 2000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .setRecLevelASS(2)
                .setThreadsCount(8)
                //.setRayPerPixel(1)
                .renderImage() //
                .writeToImage(); //
    }

    private static void addGlassAndCubes(Scene scene1) {
        Material glassMaterial = new Material()
                .setKd(0.1)
                .setKs(0.7)
                .setShininess(300)
                .setKt(1)
                .setKr(0.2);
        Material cubeMaterial = new Material()
                .setKd(0.5)
                .setKs(0.2)
                .setShininess(300)
                .setKr(0.2)
                ;
        BuildCube(scene1,BLACK,glassMaterial,-17.5,2.5,100,120,40, 60);
        BuildCube(scene1,WHITE,cubeMaterial,-13.5,-9.5,104,108,40.01, 44.01);
        BuildCube(scene1,WHITE,cubeMaterial,-5.5,-1.5,112,116,40.01, 44.01);
        //BuildCube(scene1,BROWN,material,-11.5,-3.5,106,114,60,400);
        scene1.getGeometries().add(
                new Cylinder(new Ray(new Point(-7.5,110,60),new Vector(0,0,1)),4,400)
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.8)
                                .setKr(1)
                                .setShininess(300))
                        .setEmission(new Color(GRAY)));
    }

    private static void BuildCube(Scene scene1,Color color, Material material, double startX, double endX, double startY, double endY, double startZ, double endZ) {
        scene1.getGeometries().add(
                new Polygon(
                        new Point(endX, startY, endZ),
                        new Point(endX, startY, startZ),
                        new Point(endX, endY, startZ),
                        new Point(endX, endY, endZ)
                ).setMaterial(material).setEmission(color),
                new Polygon(
                        new Point(startX, startY, endZ),
                        new Point(startX, startY, startZ),
                        new Point(startX, endY, startZ),
                        new Point(startX, endY, endZ)
                ).setMaterial(material).setEmission(color),
                new Polygon(
                        new Point(endX, startY, endZ),
                        new Point(endX, startY, startZ),
                        new Point(startX, startY, startZ),
                        new Point(startX, startY, endZ)
                ).setMaterial(material).setEmission(color),
                new Polygon(
                        new Point(endX, endY, endZ),
                        new Point(endX, endY, startZ),
                        new Point(startX, endY, startZ),
                        new Point(startX, endY, endZ)
                ).setMaterial(material).setEmission(color),
                new Polygon(
                        new Point(endX, startY, endZ),
                        new Point(endX, endY, endZ),
                        new Point(startX, endY, endZ),
                        new Point(startX, startY, endZ)
                ).setMaterial(material).setEmission(color),
                new Polygon(
                        new Point(endX, startY, startZ),
                        new Point(endX, endY, startZ),
                        new Point(startX, endY, startZ),
                        new Point(startX, startY, startZ)
                ).setMaterial(material));
    }

    private static void BuildTriangles(Scene scene1, Material material) {
        Material triangleMaterial = new Material().setKd(1).setShininess(300);
        for (int i = 0; i < 5; i++) {
            scene1.getGeometries()
                    .add(
                            new Triangle(new Point(-75+i*10, -100, 0),
                                    new Point(-65+i*10, -100, 0),
                                    new Point(-70+i*10, -20, 0))

                                    .setMaterial(triangleMaterial)
                                    .setEmission(i%2!=0?new Color(WHITE.getColor()):Color.BLACK));

        }
        for (int i = 0; i < 5; i++) {
            scene1.getGeometries()
                    .add(
                            new Triangle(new Point(-75+i*10, 100, 0),
                                    new Point(-65+i*10, 100, 0),
                                    new Point(-70+i*10, 20, 0))

                                    .setMaterial(triangleMaterial)
                                    .setEmission(i%2!=0?new Color(WHITE.getColor()):Color.BLACK));

        }
        for (int i = 0; i < 5; i++) {
            scene1.getGeometries()
                    .add(
                            new Triangle(new Point(10+i*10, -100, 0),
                                    new Point(20+i*10, -100, 0),
                                    new Point(15+i*10, -20, 0))

                                    .setMaterial(triangleMaterial)
                                    .setEmission(i%2!=0?new Color(WHITE.getColor()):Color.BLACK));

        }

        for (int i = 0; i < 5; i++) {
            scene1.getGeometries()
                    .add(
                            new Triangle(new Point(10+i*10, 100, 0),
                                    new Point(20+i*10, 100, 0),
                                    new Point(15+i*10, 20, 0))

                                    .setMaterial(triangleMaterial)
                                    .setEmission(i%2!=0?new Color(WHITE.getColor()):Color.BLACK));

        }
    }

    /**
     * add itur -- this method add the itur to the scene in the right place
     * @param scene1
     * @param material1
     */
    private static void addItur(Scene scene1, Material material1, Point place, int upOrDown) {
        for(double i = -2*Math.PI; i <=0; i+=0.05) {
            scene1.getGeometries().add(new Cylinder(new Ray(place.add(new Vector(i-0.5,-Math.sin(i),10)) , new Vector(0,0,1)), 0.4, 0.5)
                    .setEmission(new Color(GOLD.getColor()))
                    .setMaterial(material1));

        }
        for(double i = 0.01; i <= 2*Math.PI; i+=0.05) {
            scene1.getGeometries().add(new Cylinder(new Ray(place.add(new Vector(i+0.5,Math.sin(i),10)), new Vector(0,0,1)), 0.4, 0.5)
                    .setEmission(new Color(GOLD.getColor()))
                    .setMaterial(material1));
        }
        for(double i = -2*Math.PI; i <=0; i+=1) {
            scene1.getLights().add(new SpotLight(
                    new Color(WHITE.getColor()),
                    place.add(new Vector(i-0.5,-Math.sin(i)+0.01,10.6)),
                    new Vector(0,upOrDown,1))
                    .setKl(0.0001)
                    .setKq(0.00002));

        }
        for(double i = 0.01; i <= 2*Math.PI; i+=1) {
            scene1.getLights().add(new SpotLight(
                    new Color(WHITE.getColor()),
                    place.add(new Vector(i+0.5,Math.sin(i)+0.01,10.6)),
                    new Vector(0,upOrDown,1))
                    .setKl(0.0001)
                    .setKq(0.00002));
        }

    }
    //private Scene scene = new Scene.SceneBuilder("Test scene").build();
    Material material =new Material().
            setKd(0.2).
            setKs(0.8).
            setKr(0.2).
            setShininess(300);


    @Test
    public void finalPictureTest(){

        Scene scene1 = new Scene.SceneBuilder("Test scene2")./*setBackground(new Color(43, 255,0)).*/build();

        scene1.getGeometries().add(
                new Triangle(// floor
                        new Point(-70, -70, 0),
                        new Point(70, -70, 0),
                        new Point(0, -70, -2000))
                        .setEmission(new Color(223, 209, 163).reduce(3)) //
                        .setMaterial(material.setKs(1)));

        buildGenum(scene1, 0, 70, -3000);//middle
        buildGenum(scene1, -250, 40, -3000);//left
        buildGenum(scene1, 250, 100, -3000);//right


        //connection between the genums
        scene1.getGeometries().add(//left-mid connection
                new Cylinder(new Ray(new Point(-93, 93, -3100), new Vector(-1, -0.75, -0.5)), 5, 95).
                        setEmission(new Color(RED)) //up
                        .setMaterial(material),
                //right-mid connection
                new Cylinder(new Ray(new Point(150, 125, -3075), new Vector(-1, -0.85, 0.5)), 5, 70).
                        setEmission(new Color(RED)).
                        setMaterial(material));


        scene1.getLights().add(new DirectionalLight(new Color(800, 500, 0), new Vector(0, 0, -1)));

        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        ImageWriter imageWriter = new ImageWriter("finalImage", 3000,3000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .setThreadsCount(8)
                .setRecLevelASS(4)
                //.setRayPerPixel(9)
                .renderImage() //
                .writeToImage(); //

    }

    /**
     * build 1 genum
     * @param s1 scene
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    private void buildGenum(Scene s1, double x, double y, double z){
        Point p0 = new Point(0, 70, -3000);

        s1.getGeometries().add(
                new Sphere(new Point(x, y, z), 20)
                        .setEmission(new Color(BLUE )) // middle
                        .setMaterial(material),
                new Cylinder(new Ray(new Point(x + 20, y, z), new Vector(1, 0, 0.8)), 5, 60).
                        setEmission(new Color(RED)) //right
                        .setMaterial(material),
                new Cylinder(new Ray(new Point(x-20, y, z), new Vector(-1, 0.45, -2)), 5, 103).
                        setEmission(new Color(RED)) //left
                        .setMaterial(material),
                new Cylinder(new Ray(new Point(x, y + 15, z), new Vector(0.4, 1, -0.5)), 5, 40).
                        setEmission(new Color(RED)) //up
                        .setMaterial(material),
                new Cylinder(new Ray(new Point(x, y - 20, z), new Vector(0, -1, 2)), 5, 70).
                        setEmission(new Color(RED)) //down
                        .setMaterial(material),
                new Sphere(new Point(x + 80, y, z + 100), 20).
                        setEmission(new Color(BLUE)) // end right sphere
                        .setMaterial(material),
                new Sphere(new Point(x -85, y+30, z -100), 20).
                        setEmission(new Color(BLUE)) // end left sphere
                        .setMaterial(material),
                new Sphere(new Point(x + 20, y + 65, z - 100), 20)
                        .setEmission(new Color(BLUE)) // end up sphere
                        .setMaterial(material),
                new Sphere(new Point(x, y -70, z +100), 20)
                        .setEmission(new Color(BLUE)) // end down sphere
                        .setMaterial(material)

        );

    }


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
                .setKr(0.4)
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
        ImageWriter imageWriter = new ImageWriter("lightPolygons-with rec", 3000, 3000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                //.setRayPerPixel(3)
                .setRecLevelASS(4)
                .setThreadsCount(8)
                .renderImage() //
                .writeToImage(); //
    }


    /** Produce a picture of a sphere lighted by a spotlight */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.getGeometries().add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
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

    /** Produce a picture of a sphere lighted by a spotlight */
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
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.getLights().add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of two triangles lighted by a spotlight with a
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
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(50, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
