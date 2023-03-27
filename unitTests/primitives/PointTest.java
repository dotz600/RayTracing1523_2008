package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testSubtract() {

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)),
                "ERROR: Point - Point does not throw an exception");

        assertEquals(new Vector(3, 6, 9),
                new Point(1, 2, 3).subtract(new Point(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)),
                "ERROR: Point - Point does not throw an exception");


    }

    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(-1, -2, -3),
                new Point(1, 2, 3).add(new Vector(-2, -4, -6)),
                "ERROR: Point + Point does not work correctly");

    }

    @Test
    void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d, new Point(1, 1, 1).distanceSquared(new Point(2, 3, 4)), 0.0001,
                "Wrong squared distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3)), 0.0001,
                "Wrong squared distance between the point and itself");
    }

    @Test
    void testDistance() {

        // ============ Equivalence Partitions Tests ==============
        // Simple test
        assertEquals(Math.sqrt(14), new Point(1, 1, 1).distance(new Point(2, 3, 4)), 0.0001, //
                "Wrong distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distance(new Point(1, 2, 3)), 0.0001, //
                "Wrong distance between the point and itself");
    }

    @Test
    void testTestEquals() {

        assertEquals(new Point(1, 2, 3), new Point(1, 2, 3), "ERROR: equals() wrong value");
    }




}