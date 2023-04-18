package geometries;
import primitives.Point;
import org.junit.jupiter.api.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import primitives.Ray;

import java.util.List;

/*
 * Unit tests for Sphere class
 */

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Sphere tst = new Sphere(new Point(0, 0, 1), 1);
        Point argumentGetNormal = new Point(0, 0, 0);
        Vector testNormalVector = tst.getNormal(argumentGetNormal);
        Vector orthogonalVector = new Vector(1,0,0);
        //TC01: ensure |result| = 1
        assertEquals(1 , testNormalVector.length(), 0.00001,
                "ERROR: Normal Length wrong value");

        //TC02: ensure the result is orthogonal to the point
        assertTrue(isZero(testNormalVector.dotProduct(orthogonalVector)),
                "Sphere's normal is not orthogonal to the point");

        //TC03: all the point are the same - check if the result is the same
        assertEquals(tst.getNormal(argumentGetNormal), tst.getNormal(argumentGetNormal),
                "ERROR: Normal is not the same");
    }
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point (1, 0, 0),1);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
        "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())//TODO -- הופך את הנקודות למקרה ששכחנו להפוך אותן כשהחזרנו רשימה, לא ברור למה
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(1, 0, 0)));
        p1 = new Point(2,0,0);
        assertEquals(1, result.size(), "Wrong number of points");

        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray's line after sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)

        // TC12: Ray starts at sphere and goes outside (0 points)

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(0, 0, 0);
        p2 = new Point(2, 0, 0);
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getX() > result.get(1).getX())//TODO -- הופך את הנקודות למקרה ששכחנו להפוך אותן כשהחזרנו רשימה, לא ברור למה
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 0),
                new Vector(1, 0, 0)));
        p1 = new Point(2,0,0);
        assertEquals(1, result.size(), "Wrong number of points");

        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0),
                new Vector(1, 0, 0)));
        p1 = new Point(2,0,0);
        assertEquals(1, result.size(), "Wrong number of points");

        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(1, 0, 0)));
        p1 = new Point(2,0,0);
        assertEquals(1, result.size(), "Wrong number of points");

        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray's line at sphere but goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray's line after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    }
}