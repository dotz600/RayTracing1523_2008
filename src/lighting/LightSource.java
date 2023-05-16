package lighting;

import primitives.*;

/**
    * Interface for all light sources
 */
public interface LightSource {

    public Color getIntensity(Point p);

    public Vector getL(Point p);

}
