package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/*
 * Unit tests for Cylinder class
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        //============ Equivalence Partitions Tests ==============
        // TC01: on round surface
        Cylinder tst = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 10);
        //point (0.5, sqrt(3)/2, 5) is on the round surface
        Vector res = tst.getNormal(new Point(0.5,Math.sqrt(3)/2.d , 5));
        //check if it is orthogonal to the point
        if(isZero(res.dotProduct(new Vector(0.5,Math.sqrt(3)/2.d , 5))))
            fail("ERROR: Normal is not the orthogonal to ray");

        // TC02: on the base
        res = tst.getNormal(new Point(0.25, 0.5, 0));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of base is not the same as ray");

        // TC03: on the top
        res = tst.getNormal(new Point(0.25, 0.5, 10));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of top is not the same as ray");

        // =============== Boundary Values Tests ==================
        // TC04: in the center of base
        res = tst.getNormal(new Point(0, 0, 0));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of base in the center is not the same as ray");

        // TC05: in the center of top
        res = tst.getNormal(new Point(0, 0, 10));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of top in the center is not the same as ray");

        // TC06: on the base edge
        res = tst.getNormal(new Point(0, 1, 0));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of base edge is not the same as ray");

        // TC07: on the top edge
        res = tst.getNormal(new Point(1, 0, 10));
        if(!res.equals(tst.axisRay.getDir()) && !res.equals(tst.axisRay.getDir().scale(-1)))
            fail("ERROR: Normal of top edge is not the same as ray");
    }
}