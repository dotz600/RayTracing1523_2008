package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: the closet point is in the middle of the list
        Point[] pts =  {new Point(3, 0, 0), new Point(1, 0, 0), new Point(2, 0, 0)};
        List<Point> lst = new ArrayList<>();
        lst.add(pts[0]);
        lst.add(pts[1]);
        lst.add(pts[2]);
        Ray ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 1));
        assertEquals(pts[1], ray.findClosestPoint(lst), "closet point not found");

        // =============== Boundary Values Tests ==================
        //TC01: empty list
        assertNull(ray.findClosestPoint(null), "there is not closet point, should return null");
        assertNull(ray.findClosestPoint(new ArrayList<>()), "there is not closet point, should return null");

        //TC02: the closet point is the first
        lst = new ArrayList<>();
        lst.add(pts[1]);
        lst.add(pts[0]);
        lst.add(pts[2]);
        assertEquals(pts[1], ray.findClosestPoint(lst), "closet point not found");

        //TC03: the closet point is the last
        lst = new ArrayList<>();
        lst.add(pts[2]);
        lst.add(pts[0]);
        lst.add(pts[1]);
        assertEquals(pts[1], ray.findClosestPoint(lst), "closet point not found");

        //TC04: check with decimal numbers
        lst = new ArrayList<>();
        lst.add(new Point(0.9, 0, 0));
        lst.add(new Point(0.4, 0, 0));
        lst.add(new Point(0.2, 0, 0));
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        assertEquals(new Point(0.2, 0, 0), ray.findClosestPoint(lst), "closet point not found");
    }
}