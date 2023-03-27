package geometries;
import primitives.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Sphere tst = new Sphere(new Point(0, 0, 1), 1);
        assertEquals(1 , tst.getNormal(new Point(0, 0, 0)).length(),
                0.00001, "ERROR: Normal Length wrong value");
    }
}