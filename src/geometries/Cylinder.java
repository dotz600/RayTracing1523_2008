package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;


/**
 * this class represent cylinder in space
 * contain radius, ray & height
 */
public class Cylinder extends Tube{

    /** height of the Cylinder */
    private final double height;

    /**
     * constructor with parameters
     * using super constructor
     * @param ray of the cylinder
     * @param rad radius
     * @param h height
     */
    public Cylinder(Ray ray, double rad, double h){
        super(ray, rad);
        height = h;
    }

    public double getHeight() {
        return height;
    }

    /**
     * assume that the point p is on the origin
     * if the point is on base ((p - p0).length <= radius) return the direction * -1.
     * if the point is on the top ((p - topCenterPoint).length <= radius) return the direction.
     * else its on the round surface, normal = p0 + dir * (p - p0) * dir.
     * @param p get normal at point p
     * @return normal vector at point p
     */
    @Override
    public Vector getNormal(Point p) {

        //check if p is on the base (p - p0).length <= radius
        //this is true because we assumed that the point p is on the origin
        if(p.equals(this.axisRay.getP0()) || p.subtract(this.axisRay.getP0()).length() <= this.radius)
            return this.axisRay.getDir().scale(-1);

        //check if p is on the top (p - topCenterPoint (p0+height*dir)).length <= radius
        Point topCenter = this.axisRay.getP0().add(this.axisRay.getDir().scale(height));
        if(p.equals(topCenter) || p.subtract(topCenter).length() <= this.radius)
            return this.axisRay.getDir();

        //else it is on the round surface
        Point  O = this.axisRay.getP0()
                    .add((this.axisRay.getDir())
                            .scale(p.subtract(this.axisRay.getP0())
                                    .dotProduct(this.axisRay.getDir())));

        return p.subtract(O).normalize();
    }

    /**
     * Gets a ray and returns intersection GeoPoints between the ray and the geometry.
     *
     * @param ray (not NULL)
     * @return List of GeoPoints if any. else NULL
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> res = new ArrayList<>();
        List<GeoPoint> lst = super.findGeoIntersectionsHelper(ray);
        if (lst != null)
            for (GeoPoint geoPoint : lst) {
                double distance = Util.alignZero(geoPoint.point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(geoPoint);
            }

        if (res.size() == 0)
            return null;
        return res;
    }

}
