package lighting;

import primitives.*;

/**
 Interface for all light sources
 */
public interface LightSource {

    /**
     * get the intensity of the light source
     * @param p point
     * @return color
     */

    Color getIntensity(Point p);

    /**
     * get the vector from the light source to the point
     * @param p point
     * @return vector
     */

    Vector getL(Point p);

    /**
     * calculate distance between the point and light position
     * @param point - the point to get the distance from
     * @return Distance (double)
     */
    double getDistance(Point point);

}
