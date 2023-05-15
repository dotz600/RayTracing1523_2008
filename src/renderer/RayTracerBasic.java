package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {


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

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersectionsHelper(ray);
        if (intersections == null)
            return scene.getBackground();
        GeoPoint closestGeopoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestGeopoint, ray);
    }


    /**
     * calculate the color of the pixel
     * @param closestPoint the closest intersection point to the camera
     * @param ray the ray that intersect with the scene
     * @return the color of the pixel
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {

        return scene.getAmbientLight().getIntensity().add(
                calcLocalEffects(closestPoint, ray));

    }


    /**
     * calculate the color using all the light sources in the scene
     * @param gp the closest intersection point to the camera
     * @param ray the ray that intersect with the scene
     * @return the color of the pixel
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {

        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {//sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material,nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        return color;
    }


    /**
     * calculate the diffusive component of the color
     * Kd * |nl|
     * @param material the material of the geometry
     * @param nl normal * light direction
     * @return Double3 - the diffusive component of the color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calculate the specular component of the color
     * Ks * (max(0,-V * R))^nShininess
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
