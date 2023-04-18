package geometries;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/*
 * Unit tests for Plane class
 */
class PlaneTest {

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

        Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));

    }
}