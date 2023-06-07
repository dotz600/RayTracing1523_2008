package renderer;

import jdk.jshell.spi.ExecutionControl;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable.GeoPoint;


import static java.lang.System.out;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracerBase {

    private static final Double3 INITIAL_K = new Double3(1.0);//initial value of k
    private static final int MAX_CALC_COLOR_LEVEL = 10;//maximum level of recursion
    private static final double MIN_CALC_COLOR_K = 0.001;//minimum value of k
    private static final double DELTA = 0.1;


    /**
     * calculate whether the point is shaded or not
     * @param gp
     * @param light
     * @param l
     * @param n
     * @return Double3 object that represent the transparency of the point
     */
    private Double3 transparency(GeoPoint gp, LightSource light,
                                 Vector l, Vector n) {
        //create a ray from the point to the light source
        Vector lightDirection = l.scale(-1);
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        //use the 3 parameters to create a ray
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        //check if the ray intersect with any geometry
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersectionsHelper(lightRay);
        Double3 result = Double3.ONE;
        //if there is no intersection return (1,1,1), represent no shading
        if (intersections == null || intersections.isEmpty()) {
            return result;
        }

        //calculate the distance between the point and the light source
        double lightDistance = light.getDistance(point);
        for (GeoPoint geop : intersections) {
            //if the distance between the point and the light source
            // is smaller than the distance between the point and the intersection
            if (alignZero(point.distanceSquared(geop.point) - lightDistance) <= 0) {
                result = geop.geometry.getMaterial().kT.product(result);
                //if the transparency is smaller than the minimum value return (0,0,0), represent full shading
                if (result.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        return result;
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
    private Color traceRay(Ray ray) {

        GeoPoint closestGeopoint = findClosestIntersection(ray);
        return closestGeopoint == null ? scene.getBackground()
                                       : calcColor(closestGeopoint, ray );
    }

    /**
     * calculate the color of each ray in the list and return the average color
     * @param rays  rays list that intersect with the scene in the same pixel
     * @return the average color of the rays
     */
    @Override
    public Color traceRays(LinkedList<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays)
            color = color.add(traceRay(ray));

        return color.reduce(rays.size());
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
        //if n*v = 0, the point is not shaded
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.getLights()) {

            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            //nl * nv > 0, the point is not shaded
            if (nl * nv > 0) {//sign(nl) == sign(nv)
                Double3 result = transparency(gp,lightSource, l, n);
                //if the point is not shaded, not equal to (0,0,0)
                if (!result.equals(Double3.ZERO)) {
                    //calculate the color of the point, using the light source intensity multiplied by the transparency
                    Color lightIntensity = lightSource.getIntensity(gp.point).scale(result);
                    color = color.add(
                            //add the diffusive and specular effects
                            lightIntensity.scale(calcDiffusive(material.kD,nl)),
                            lightIntensity.scale(calcSpecular(material, n, l, nl, v)));
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
    private Color calcGlobalEffects(GeoPoint gp, Vector dir,
                                    int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = dir;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGlobalEffect(constructReflectedRay(gp.point, v, n),
                level, k, material.kR)
                .add(calcColorGlobalEffect(constructRefractedRay(gp.point, v, n),
                        level, k, material.kT));
    }


    /**
     * construct the reflected ray
     * @param point
     * @param dir
     * @param normal
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector dir, Vector normal) {

        Vector normalDir = normal.scale(-2 * dir.dotProduct(normal));
        Vector result = dir.add(normalDir);
        /* use the constructor with 3 arguments to move the head */
        return new Ray(point, result, normal);
    }
    /**
     * construct the refracted ray
     * @param point
     * @param dir
     * @param normal
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point point, Vector dir, Vector normal) {

        return new Ray(point, dir, normal);
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
                ? Color.BLACK : calcColor(gp, ray.getDir(), level-1, kkx).scale(kx);
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
