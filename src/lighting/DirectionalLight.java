package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {

    Vector direction;

    /**
     * constructor - initializes the direction and the light intensity
     *
     * @param intensity the light intensity
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * get the intensity of the light source at a point
     * direction light intensity is constant
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light source at the point
     */
    @Override
    public Color getIntensity(Point p) {
       return getIntensity();
    }

     /**
     * get the direction of the light source at a point
     * direction lightDirection is constant
      *
     * @param p the point to get the direction at
     * @return the direction of the light source at the point
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }
}
