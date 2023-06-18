package lighting;

import primitives.*;

/**
    * Interface for all light sources
 */
public interface LightSource {

    Color getIntensity(Point p);

    Vector getL(Point p);
    double getDistance(Point point);

}
