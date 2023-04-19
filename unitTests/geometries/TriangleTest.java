package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle tst = new Triangle(pts[0], pts[1], pts[2]);
        //TC01: ensure there are no exceptions
        assertDoesNotThrow(() -> tst.getNormal(new Point(0, 0, 1)), "");

        // generate the test result
        Vector result = tst.getNormal(new Point(0, 0, 1));
        //TC02: ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");

        //TC03: ensure the result is orthogonal to the triangle's plane
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0]))),
                    "Triangle's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);


    }
}