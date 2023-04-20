package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
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

        Triangle triangle = new Triangle(new Point(1, 1, 0),
                new Point(-1, 1, 0), new Point(0, 3, 0));

        //==============equivalence partition tests=================
        //TC01: Ray's line is inside the triangle (1 point)
        assertEquals(List.of(new Point(0, 2.5, 0)),
                triangle.findIntersections(new Ray(new Point(0, 1.5, -1), new Vector(0, 1, 1))),
                "Ray's line out of triangle");

        assertEquals(List.of(new Point(0, 1.5, 0)),
                triangle.findIntersections(new Ray(new Point(0, 1.5, -1), new Vector(0, 0, 1))),
                "Ray's line out of triangle");


        //TC02: Ray's line is outside against the edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 1), new Vector(0, -1, -1))),
                "Ray's line should be out of triangle");

        //TC03: Ray's line is outside against the vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(1.5, 0.5, 1), new Vector(2, -1, -1))),
                "Ray's line against vertex should be out of triangle");

        //==============boundary values tests=================
        //TC04: Ray's line intersection is in the vertex  (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge should be out of triangle");

        //TC05: Ray's line intersection is on the edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge should be out of triangle");

        //TC06: intersection is on the edge's continuation (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(2, 1, -1), new Vector(0, 0, 1))),
                "intersection on the edge continuation should be out of triangle");
    }
}