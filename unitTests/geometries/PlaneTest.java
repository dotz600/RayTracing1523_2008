package geometries;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {

    @Test
    void testConstructor() {

        // =============== Boundary Values Tests ==================
        // TC01: 2 points are the same
        Point[] pts =
                { new Point(0, 0, 1), new Point(0, 0, 0.9999999), new Point(0, 1, 0)};
        // ensure that exception is thrown
        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(pts[0], pts[1], pts[2]),
                "ERROR: Plane constructor does not throw an exception when the points are on the same line");

        // TC02: 3 points are on the same line
        assertThrows(IllegalArgumentException.class,
                ()-> new Plane(new Point(1, 0, 0),new Point(2, 0, 0), new Point(3, 0, 0)),
                "ERROR: Plane constructor does not throw an exception when the points are on the same line");
    }

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Plane tst = new Plane(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tst.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tst.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0]))),
                    "Plane's normal is not orthogonal to one of the edges");
    }
}