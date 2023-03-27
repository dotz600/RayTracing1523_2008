package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        Tube tst = new Tube(new Ray(new Point(0, 0, 1), new Vector(1, 0, 0)), 1);
        assertEquals(1 , tst.getNormal(tst.getAxisRay().getP0().subtract()).length(),
                0.00001, "ERROR: Normal Length wrong value");

    }
}