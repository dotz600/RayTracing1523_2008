package lighting;

import primitives.*;

/**
 //TODO - Documention
 */
public interface LightSource {

    public Color getIntensity(Point p);

    public Vector getL(Point p);

}
