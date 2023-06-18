package lighting;

import primitives.Color;

/**
    * Abstract class for all light sources
 */
abstract class Light {

    private final Color intensity;

    /**
     * constructor - initializes the light intensity
     * @param intensity the light intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    //------Getters-----------
    public Color getIntensity() {
        return intensity;
    }
}
