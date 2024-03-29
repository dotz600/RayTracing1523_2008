package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
* unit Tests for Point Class
*/
class PointTest {

    /**
     * Test method for {@link Point#subtract(Point)}.
     */
    @Test
    void testSubtract() {

        // =============== Boundary Values Tests ==================
        //TC01: simple test
        assertEquals(new Vector(3, 6, 9),
                new Point(1, 2, 3).subtract(new Point(-2, -4, -6)),
                "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC01: zero vector should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)),
                "ERROR: Point - Point does not throw an exception");


    }

    /**
     * Test method for {@link Point#add(Vector)}.
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(new Point(-1, -2, -3),
                new Point(1, 2, 3).add(new Vector(-2, -4, -6)),
                "ERROR: Point + Point does not work correctly");

        //no BVA because point can be zero
    }

    /**
     * Test method for {@link Point#distanceSquared(Point)}.
     */
    @Test
    void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d, new Point(1, 1, 1).distanceSquared(new Point(2, 3, 4)), 0.0001,
                "Wrong squared distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC02: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3)), 0.0001,
                "Wrong squared distance between the point and itself");
    }

    /**
     * Test method for {@link Point#distance(Point)}.
     */
    @Test
    void testDistance() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(Math.sqrt(14), new Point(1, 1, 1).distance(new Point(2, 3, 4)), 0.0001, //
                "Wrong distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC02: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distance(new Point(1, 2, 3)), 0.0001, //
                "Wrong distance between the point and itself");
    }

    /**
     * Test method for {@link Point#equals(Object)}.
     */
    @Test
    void testTestEquals() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test - equal
        assertEquals(new Point(1, 2, 3), new Point(1, 2, 3),
                "ERROR: equals() wrong value");

        //TC02: Simple test - not equal
        assertNotEquals(new Point(1, 2, 3), new Point(1, 2, 4),
                "ERROR: equals() wrong value");
    }




}