package renderer;

import static java.awt.Color.*;

import geometries.Cylinder;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

public class FinalImageTests {

    final Color BROWN = new Color(102, 51, 0);
    static final Color GOLD = new Color(255, 215, 0);
    static final Color WHITE = new Color(255, 255, 255);
    static final Color BLACK = new Color(0,0,0);

    Material material =new Material().
            setKd(0.2).
            setKs(0.8).
            setKr(0.2).
            setShininess(300);
    Material material1 = new Material()
            .setKs(0.8)
            .setKd(0.1)
            .setKr(1)
            .setShininess(300);
    @Test
    public void BackgammonPictureTest() {
        Scene scene1 = new Scene.SceneBuilder("Test scene1").build();

        setBackgammonSceneGeometry(scene1);
        setSceneBackgammonLight(scene1);

        Camera camera1 = new Camera(
                new Point(145,-545,310),
                new Vector(-0.3,1,-0.55),
                new Vector(0,0.55,1))
                .setVPSize(150, 150)
                .setVPDistance(450);

        ImageWriter imageWriter = new ImageWriter("Backgammon", 3000, 3000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .setRecLevelASS(3)
                .setThreadsCount(8)
                //.setRayPerPixel(1)
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    public void DNA_PictureTest(){

        Scene scene1 = new Scene.SceneBuilder("Test scene2").build();

        buildDNA_scene(scene1);

        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        ImageWriter imageWriter = new ImageWriter("DNA", 3000,3000);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .setThreadsCount(8)
                .setRecLevelASS(3)
                //.setRayPerPixel(9)
                .renderImage() //
                .writeToImage(); //

    }

    /**
     * test for the picture in the presentation
     */
    @Test
    public void polygonTest() {
        Scene scene1 = setPolygonScene();

        Camera camera1 = new Camera(new Point(-380,-380,380),
                new Vector(1,1,-1), new Vector(1,0,1))
                .setVPSize(150, 150).setVPDistance(530);

        ImageWriter imageWriter = new ImageWriter("lightPolygons-with rec", 3000, 3000);

        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                //.setRayPerPixel(3)
                .setRecLevelASS(3)
                .setThreadsCount(8)
                .renderImage() //
                .writeToImage(); //
    }

    private void setBackgammonSceneGeometry(Scene scene1) {
        double startX = -85, endX = -10, startY = -100, endY = -110, startZ = 0, endZ = 10;
        int up = 1, down = -1;

        BuildTriangles(scene1, material);
        BuildCube(scene1, BROWN, material, startX, endX, startY, endY, startZ, endZ);//left down
        addDecoration(scene1, material1, new Point(-32.5-17,-105,0), down);
        BuildCube(scene1, BROWN, material, startX, endX, -startY, -endY, startZ, endZ);//left up
        addDecoration(scene1, material1, new Point(-32.5-17,105,0), up);
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, startY, endY, startZ, endZ );//right down
        addDecoration(scene1, material1, new Point(17.5+17,-105,0), down);
        BuildCube(scene1, BROWN, material, -startX -15, -endX-15, -startY, -endY, startZ, endZ );//right up
        addDecoration(scene1, material1, new Point(17.5+17,105,0), up);
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
    }

    private static void setSceneBackgammonLight(Scene scene1) {
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
                .setKr(0.2);
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
     * add decoration to the scene
     * @param scene1 the scene
     * @param material1 the material
     * @param place the place to add the decoration
     * @param upOrDown if the decoration is up or down
     */
    private static void addDecoration(Scene scene1, Material material1, Point place, int upOrDown) {
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


    private void buildDNA_scene(Scene scene1) {
        scene1.getGeometries().add(
                new Triangle(// floor
                        new Point(-70, -70, 0),
                        new Point(70, -70, 0),
                        new Point(0, -70, -2000))
                        .setEmission(new Color(223, 209, 163).reduce(3)) //
                        .setMaterial(material.setKs(1)));

        buildDNA(scene1, 0, 70, -3000);//middle
        buildDNA(scene1, -250, 40, -3000);//left
        buildDNA(scene1, 250, 100, -3000);//right


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
    }


    private static Scene setPolygonScene() {
        Scene scene1 = new Scene.SceneBuilder("Test scene1").build();
        Material material= new Material()
                .setKd(new Double3(0.2, 0.6, 0.4)
                ).setKs(new Double3(0.2, 0.4, 0.3))
                .setShininess(301)
        ,material1 = new Material()
                .setKd(0.2)
                .setKs(1)
                .setKr(0.4)
                .setShininess(300);

        buildPolygonSphere(scene1, material, material1);

        scene1.getLights().add(new PointLight(new Color(800,300,0), new Point(-80,80,60)).setKl(0.001).setKq(0.0002));
        scene1.getLights().add(new PointLight(new Color(800,300,0), new Point(80,-80,60)).setKl(0.001).setKq(0.0002));
        scene1.getLights().add(new PointLight(new Color(500,200,0), new Point(80,-80,60)).setKl(0.001).setKq(0.0002));
        return scene1;
    }

    private static void buildPolygonSphere(Scene scene1, Material material, Material material1) {
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
    }

    private static void BuildCube(Scene scene1,Color color, Material material,
                                  double startX, double endX, double startY, double endY, double startZ, double endZ) {

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

    /**
     * build 1 genum
     * @param s1 scene
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    private void buildDNA(Scene s1, double x, double y, double z){
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

}
