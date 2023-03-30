package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.*;

/*class TubeTest {

    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        Tube tst = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1);
        assertEquals(1 , tst.getNormal(new Point(0, 1, 0)).length(),
                0.00001, "ERROR: Normal Length wrong value");

        assertTrue(isZero(tst.getNormal(new Point(0, 1, 0)).dotProduct(new Vector(1, 0, 1))),
                "ERROR: Normal is not orthogonal to the ray");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,
                ()-> tst.getNormal(new Point(1, 1, 0)),
                "ERROR: getNormal() for Tube does not throw an exception when the point is on the ray");
    }
}*/