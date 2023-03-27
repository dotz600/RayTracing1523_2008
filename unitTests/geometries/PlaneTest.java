package geometries;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Plane tst = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(1 , tst.getNormal().length(),0.00001, "ERROR: Normal Length wrong value");
    }

    @Test
    void testTestGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Plane tst = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(1 , tst.getNormal().length(),0.00001, "ERROR: Normal Length wrong value");
        double res = 1 + 1.0/3.0;
        assertEquals(new Vector(res, res, res),
                tst.getNormal(new Point(1, 1, 1)), "ERROR: Normal Length wrong value");
    }
}