package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for Vector class
 */
class VectorTest {

    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(new Vector(-1, -2, -3),
                new Vector(1, 2, 3).add(new Vector(-2, -4, -6)),
                "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: test adding v + (-v)
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception");
    }

    @Test
    public void testSubtract() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                new Vector(2, 3, 4).subtract(new Vector(1, 2, 3)),
                "ERROR: Vector - Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: subtracting same vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)),
                "ERROR: Vector - Vector does not throw an exception");
    }

    @Test
    void testScale() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).scale(2),
                "Wrong vector scale");

        // =============== Boundary Values Tests ==================
        //TC02: scale by 0 - must throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).scale(0d), "Scale by 0 must throw exception\n");
    }

    @Test
    void testDotProduct() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(-28d,
                new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)),
                0.00001,
                "dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        //TC02: dotProduct for orthogonal vectors
        assertEquals(0d,
                new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2)),
                0.00001,
                "dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001,
                "crossProduct() wrong result length");

        //TC03-04: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),
                "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)),
                "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        //TC05: Test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    @Test
    void testLengthSquared() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(14d,
                new Vector(1, 2, 3).lengthSquared(),
                0.00001, "ERROR: lengthSquared() wrong value");

        // =============== Boundary Values Tests ==================
        //TC02: test lengthSquared of zero vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0).lengthSquared(),
                "lengthSquared() of zero vector does not throw an exception");
    }

    @Test
    void testLength() {

        //================== Boundary Values Tests ==================
        //TC01: simple test
        assertEquals(5d,
                new Vector(0, 3, 4).length(),
                0.00001, "length() wrong value");

        // =============== Boundary Values Tests ==================
        //TC02: test length of zero vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0).length(),
                "length() of zero vector does not throw an exception");
    }

    @Test
    void testNormalize() {

        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple test - check normalized vector is a unit vector
        assertEquals( 1d, n.lengthSquared(), 0.00001,
                "wrong normalized vector length");

        //TC02: Simple test
        assertEquals( new Vector(0, 0.6, 0.8), n,
                "wrong normalized vector");

        // =============== Boundary Values Tests ==================
        //TC03: test direction of normalize vector
        assertThrows( IllegalArgumentException.class,
                () -> v.crossProduct(n) ,
                "normalized vector is not in the same direction");
    }

    @Test
    public void testPointSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple test
        assertEquals(new Vector(3, 6, 9),
                new Vector(1, 2, 3).subtract(new Vector(-2, -4, -6)),
                "ERROR: Vector - Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: subtracting same vector should throw exception
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)),
                "ERROR: Vector - Vector does not throw an exception");

    }

    @Test
    void testTestEquals() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple test - equal vectors
        assertEquals(new Vector(1, 2, 3), new Vector(1, 2, 3),
                "ERROR: equals() wrong value");

        //TC02: Simple test - not equal vectors
        assertNotEquals(new Vector(1, 2, 3), new Vector(1, 2, 4),
                "ERROR: equals() wrong value");
    }
}


