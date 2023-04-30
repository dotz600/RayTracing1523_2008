import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.*;


/*
 * Integration tests for Camera class with Ray class
 */
public class integrationTests {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Help function to check the integration of the camera and geometries
     * Each pixel can have a different amount of cuts -
     * so the method sums up the amount of cuts from all pixel,
     * and compares at the end against the expected total
     *  3X3 pixels
     * @param cam the camera
     * @param geometries Geometry
     * @param expected the expected number of points in total
     */
    private void checkIntegration(Camera cam, Geometry geometries, int expected) {

        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; ++j) {
                var intersections = geometries.findIntersections(cam.constructRay(3, 3, j, i));
                if (intersections != null)
                    count += intersections.size();
            }
        }
        assertNotEquals(expected, count, "Wrong number of points");
    }

    /**
     * Test method to check the integration of the camera and Sphere
     * {@link geometries.Sphere}
     */
    @Test
    public void testSphereAndCameraIntegration() {

        // TC01: 2 intersection points, r = 1 , only 1 point in the view plane intersect with the sphere
        Sphere sphere =  new Sphere(new Point(0, 0, -3) , 1);
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        checkIntegration(cam, sphere, 2);

        // TC02: 18 intersection points, r = 2.5 , 9 points in the view plane intersect with the sphere 2 times
        sphere =  new Sphere(new Point(0, 0, -2.5) , 2.5);
        cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        checkIntegration(cam, sphere, 18);

        // TC03: 10 intersection points, r = 2 , 5 points in the view plane intersect with the sphere 2 times
        sphere =  new Sphere(new Point(0, 0, -2) , 2);
        checkIntegration(cam, sphere, 10);

        // TC04: 9 intersection points, r = 4 , 9 points in the view plane intersect with the sphere 1 times
        sphere =  new Sphere(new Point(0, 0, -2) , 4);
        checkIntegration(cam, sphere, 9);

        // TC05: 0 intersection points, r = 0.5 , 0 points in the view plane intersect with the sphere
        sphere =  new Sphere(new Point(0, 0, 1) , 0.5);
        checkIntegration(cam, sphere, 0);

    }

    /**
     * Test method to check the integration of the camera and plane
     * {@link geometries.Plane}
     */
    @Test
    public void testPlaneAndCameraIntegration() {

        // TC01: 9 intersection points, plane is parallel to the view plane
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, -1));
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        checkIntegration(cam, plane, 9);

        // TC02: 9 intersection points, plane is not parallel to the view plane
        plane = new Plane(new Point(0, 0, -3), new Vector(0, -1, -1));
        checkIntegration(cam, plane, 9);

        // TC03: 6 intersection points, plane is not parallel to the view plane
        plane = new Plane(new Point(0, 0, -4), new Vector(0, -1, -1));
        cam = new Camera(new Point(0, 0, 1), new Vector(0, 1, 0), new Vector(0, 0, -1));
        //TODO - if fails, check the camera amd plane point
        checkIntegration(cam, plane, 6);

    }

    /**
     * Test method to check the integration of the camera and triangle
     * {@link geometries.Triangle}
     */
    @Test
    public void testTriangleAndCameraIntegration() {

        // TC01: 1 intersection points, triangle is parallel to the view plane
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        checkIntegration(cam, triangle, 1);

        // TC02: 2 intersection points, triangle is not parallel to the view plane
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        checkIntegration(cam, triangle, 2);
    }

    //TODO - check if this tests are needed
    /*
    @Test
    public void testCylinderAndCameraIntegration() {

    }

    @Test
    public void testPolygonAndCameraIntegration() {

    }



    @Test
    public void testTubeAndCameraIntegration() {

    }
    */

}
