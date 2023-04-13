package geometries;
import primitives.Point;
import org.junit.jupiter.api.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/*
 * Unit tests for Sphere class
 */

class SphereTest {

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
        //TODO - TC03: all the point are the same

    }
}