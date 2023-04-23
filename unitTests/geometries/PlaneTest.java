package geometries;
import primitives.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/*
 * Unit tests for Plane class
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Vector)}  Plane}.
     */
    @Test
    void testConstructor() {
        //============Boundary Values Tests=============
        //TC01: two point are the same - should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(0,0, 0), new Point(0, 1, 0)),
                "ERROR: to point are the almost the same should throw exception");

        //TC02: the points are on the same line should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(2, 0, 0)),
                "ERROR: the points are on the same line should throw exception");


    }
    /**
     * Test method for {@link Plane#getNormal()}.
     */
    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        //TC01: simple test- check normal is a unit vector
        Plane tst = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(1 , tst.getNormal().length(),0.00001,
                "ERROR: Normal Length wrong value");

        //TC02: check normal is orthogonal to the plane
        tst = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertTrue(isZero(tst.getNormal().dotProduct(new Vector(-1, 1, 0))),
                "ERROR: Normal from getNormal() without point is not orthogonal to the plane");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal_Point() {

        //============ Equivalence Partitions Tests ==============
        Plane tst = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        //TC01: simple test-check normal is a unit vector
        assertEquals(1 , tst.getNormal(new Point(0, 0, 1)).length(),0.00001,
                "ERROR: Normal Length wrong value");


        //TC03: check normal is orthogonal to the plane
        tst = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertTrue(isZero(tst.getNormal(new Point(0.5, 0.5, 0)).dotProduct(new Vector(-1, 1, 0))),
                "ERROR: Normal is not orthogonal to the plane");

        //TC04: all points are the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 0, 0)),
                "ERROR: all points are the same");

        //TC05: get normal for the same point should return the same vector
        assertEquals(tst.getNormal(new Point(0.5, 0.5, 0)), tst.getNormal(new Point(0.5, 0.5, 0)),
                "ERROR: get normal for the same point should return the same vector");


    }
    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray start before the plane, not parallel to plane, intersect the plane one time
        Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));
        assertEquals(List.of(new Point(0.46,0.22,0.32)), plane.findIntersections(
                new Ray(new Point(0,1.5,0), new Vector(0.46,-1.28,0.32))),
                "ERROR: Ray start before the plane, not parallel to plane, intersect the plane one time, not the expected point");

        //TC02: Ray start before the plane, not parallel to plane,do not intersect the plane
        Plane planeSurface = new Plane(new Point(1,0,1), new Point(0,1,1), new Point(0,0,1));
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(0,0,2), new Vector(-1,2,1.5))),
                "ERROR: Ray start before the plane, not parallel to plane,should not intersect the plane");

        //=============Boundary Values Tests=================
        //TC03: ray inside the plane, parallel to plane
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(1,1,1), new Vector(1,1,0))),
                "ERROR: ray inside the plane, parallel to plane, intersect the plane infinite times, should return null");

        //TC04: ray outside the plane parallel to plane - zero intersections point
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(2,2,2), new Vector(1,1,0))),
                "ERROR: ray outside the plane parallel to plane, should return null");

        //TC06: ray start before the plane, vertical to plane
        assertEquals(List.of(new Point(1,1,1)), planeSurface.findIntersections(
                new Ray(new Point(1,1,0), new Vector(0,0,1))),
                "ERROR: ray start before the plane, vertical to plane, should return the point");

        //TC07: ray start inside the plane, vertical to plane
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(1,1,1), new Vector(0,0,1))),
                "ERROR: ray start inside the plane, vertical to plane, should return null");

        //TC08: ray start after the plane, vertical to plane
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(1,1,2), new Vector(0,0,1))),
                "ERROR: ray start after the plane, vertical to plane, should return null");

        //TC09:ray start inside the plane - not vertical and not parallel to plane
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(1,1,1), new Vector(1,1,1))),
                "ERROR: ray start inside the plane - not vertical and not parallel to plane, should return null");

        //TC10: the ray start inside the plane in plane q0 point - not vertical and not parallel to plane
        assertNull(planeSurface.findIntersections(
                new Ray(new Point(1,0,1), new Vector(1,1,1))),
                "ERROR: the ray start inside the plane in plane q0 point, should return null");
    }
}