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
    void testTestGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Plane tst = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        //TC01: simple test-check normal is a unit vector
        assertEquals(1 , tst.getNormal().length(),0.00001,
                "ERROR: Normal Length wrong value");

        //TC02: check normal is as expected
        double res = 1 + 1d/Math.sqrt(3);
        assertEquals(new Vector(res, res, res),
                tst.getNormal(new Point(1, 1, 1)), "ERROR: Normal wrong value");

        //TC03: check normal is orthogonal to the plane
        tst = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertTrue(isZero(tst.getNormal(new Point(0.5, 0.5, 0)).dotProduct(new Vector(-1, 1, 0))),
                "ERROR: Normal is not orthogonal to the plane");
    }
}