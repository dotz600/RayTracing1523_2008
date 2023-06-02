package renderer;

import jdk.jshell.spi.ExecutionControl;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracerBase {

    private static final Double3 INITIAL_K = new Double3(1.0);//initial value of k
    private static final int MAX_CALC_COLOR_LEVEL = 10;//maximum level of recursion
    private static final double MIN_CALC_COLOR_K = 0.001;//minimum value of k

    /**
     * check if the point is shaded by another geometry
     * @param gp
     * @param l
     * @param n
     * @return
     */
    private boolean unshaded(GeoPoint gp,LightSource lightSource, Vector l, Vector n) {

            if (gp.geometry.getMaterial().kT.equals(Double3.ZERO)) {

                Vector lightDirection = l.scale(-1);//from point to light source
                Ray lightRay = new Ray(gp.point, lightDirection, n);
                List<GeoPoint> intersections = scene.getGeometries().findGeoIntersectionsHelper(lightRay);

                if (intersections == null)
                    return true;

                double distancePoint_lightSource = lightSource.getDistance(gp.point);//מרחק בין ראש הקרן לבין מקור האור

                for (GeoPoint intersection : intersections) {

                    double dis = intersection.point.distanceSquared(gp.point);
                    if (dis < distancePoint_lightSource)//מרחקים בין ראש הקרן לבין נקודות חיתוך
                        return false;
                }
            }
            return true;

    }


    /**
     * constructor with scene parameter
     * @param scene the scene to render
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * get the color of the pixel where the ray intersect with the scene
     * if there is no intersection return the background color
     *
     * @param ray  the ray that intersect with the scene
     * @return Color of the pixel
     */
    @Override
    public Color traceRay(Ray ray) {

        GeoPoint closestGeopoint = findClosestIntersection(ray);
        return closestGeopoint == null ? scene.getBackground()
                                       : calcColor(closestGeopoint, ray );
    }

    /**
     * find the closest intersection point to the ray head
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersectionsHelper(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * calculate the color of the pixel
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray){

        return calcColor(gp,ray.getDir(),MAX_CALC_COLOR_LEVEL,INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }
    /**
     * calculate the color of the pixel
     * @param closestPoint the closest intersection point to the camera
     * @param dir the ray direction that intersect with the scene
     * @return the color of the pixel
     */
    private Color calcColor(GeoPoint closestPoint, Vector dir, int level, Double3 k) {

        Color color = calcLocalEffects(closestPoint,dir);
        return 1 == level ? color
                          : color.add(calcGlobalEffects(closestPoint, dir, level, k));
    }


    /**
     * calculate the color using all the light sources in the scene
     * @param gp the closest intersection point to the camera
     * @param dir the ray direction that intersect with the scene
     * @return the color of the pixel
     */
    private Color calcLocalEffects(GeoPoint gp, Vector dir) {

        Color color = gp.geometry.getEmission();
        Vector v = dir;
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.getLights()) {

            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {//sign(nl) == sign(nv)
                if (unshaded(gp,lightSource,l,n)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                            iL.scale(calcDiffusive(material.kD, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculate the global effects of the scene
     * @param gp
     * @param dir
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector dir, int level, Double3 k){

       //TODO -- Implement this method correctly

       return null;
    }

    private Ray constructReflectedRay(Point point, Vector dir, Vector normal) {

        Vector normalDir = normal.scale(-2 * dir.dotProduct(normal));
        Vector result = dir.add(normalDir);
        /* use the constructor with 3 arguments to move the head */
        return new Ray(point, result, normal);
    }

    /**
     * calculate the global effects of the scene
     * @param ray
     * @param level
     * @param k
     * @param kx
     * @return
     */
    private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx){

        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.getBackground().scale(kx);

        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray.getDir(),level - 1, kkx);
    }


    /**
     * calculate the diffusive component of the color<p>
     * Kd * | nl |
     * @param k_D the Material.kD of the geometry
     * @param nl normal * light direction
     * @return Double3 - the diffusive component of the color
     */
    private Double3 calcDiffusive(Double3 k_D, double nl) {

        return k_D.scale(Math.abs(nl));
    }

    /**
     * calculate the specular component of the color<p>
     * Ks * (max(0,-V * R))^n Shininess
     * @param material the material of the geometry
     * @param n normal
     * @param l light direction
     * @param nl normal * light direction
     * @param v the direction of the ray
     * @return Double3 - the specular component of the color
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
       Vector r = l.subtract(n.scale(2 * nl));
       double vr = alignZero(v.dotProduct(r) * -1);
       return vr <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(vr, material.nShininess));
    }
}
