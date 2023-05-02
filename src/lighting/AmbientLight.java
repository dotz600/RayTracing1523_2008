package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * TODO -- Documentation here
 */
public class AmbientLight {

    Color intensity;

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK,Double3.ZERO);

    /**
     * TODO -- Documentation here
     */
    public AmbientLight(Color color_Ia, Double3 color_Ka) {

            intensity = color_Ia.scale(color_Ka);
    }

    /**
     * TODO -- Documentation here
     */
    public AmbientLight(Double color_Ka) {

        intensity = intensity.scale(color_Ka);
    }

    public Color getIntensity() {
        return intensity;
    }
}
