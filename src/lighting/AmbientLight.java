package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a scene.
 * Ambient light is a uniform level of illumination that affects all objects equally.
 * extend abstract class Light - get from it Color intensity
 */
public class AmbientLight extends Light {


    /**
     * A constant representing no ambient light (black color and zero intensity).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an AmbientLight object with the specified color and intensity.
     *
     * @param color_Ia The color of the ambient light.
     * @param color_Ka The intensity of the ambient light.
     */
    public AmbientLight(Color color_Ia, Double3 color_Ka) {

        super(color_Ia.scale(color_Ka));
    }

    /**
     * Constructs an AmbientLight object with a single intensity value, assuming a black scale color.
     *
     * @param color_Ka The intensity of the ambient light.
     */
    public AmbientLight(Double color_Ka) {

        super(Color.BLACK.scale(color_Ka));
    }

}
