package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/** Testing Polygons
*/
class GeometriesTest {

    /** Test method for {@link geometries.Geometries#add(Intersectable...)}. */
    @Test
    void add() {

    }

    /** Test method for {@link geometries.Geometries#findIntersections(Ray)}. */
    @Test
    void findIntersections() {

        // ============ Boundary Values Tests ==============
        // TC01: Empty list of geometries - no intersections expected
        Geometries geometries = new Geometries();
        assertNull(geometries.findIntersections(new Ray(new Point(1,1,1), new Vector(0, 0, 1))));

        // TC02: No intersections expected - ray is outside the geometries
        geometries.add(new Sphere(new Point(3, 0, 0),1), new Plane(new Point(1, 0, 0), new Vector(0, 1, 0)));
        assertNull(geometries.findIntersections(new Ray(new Point(0,1,0), new Vector(0, 1, 0))));

        // TC03: Only one intersection expected - ray crosses the geometries once
        assertEquals(1, geometries.findIntersections(new Ray(new Point(2.5,0.5,0), new Vector(1, 0, 0))).size());

        // TC04: Three intersections expected - ray crosses the geometries three times (twice in the sphere and once in the plane)
        assertEquals(3, geometries.findIntersections(new Ray(new Point(0,3,0), new Vector(1, -1, 0))).size());

        // =============== Equivalence Partitions Tests ==============
        // TC05: Only one intersection expected - ray crosses just one geometry
        assertEquals(1, geometries.findIntersections(new Ray(new Point(1,1,0), new Vector(0, -1, 0))).size());

    }

}