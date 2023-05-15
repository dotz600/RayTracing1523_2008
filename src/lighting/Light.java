package lighting;

import primitives.Color;

/**
 //TODO - Documentation
 */
abstract class Light {

    private Color intensity;

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
