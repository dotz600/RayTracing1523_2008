package lighting;
import primitives.*;
public class PointLight extends Light implements LightSource{

    private Point position; //the position of the light
    private double kC = 1 , kL = 0 , kQ = 0;//attenuation coefficients, constant, linear, quadratic


    // ***************** Constructor ********************** //

    /**
     * constructor - initializes the position and the light intensity
     *
     * @param position of the light source
     * @param intensity of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    // ***************** Getters/Setters ********************** //
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().reduce(kC + kL*d + kQ*d*d);
    }

    @Override
    public Vector getL(Point p) {

        return p.subtract(position).normalize();
    }

    /**
     * chaining method - set constant attenuation coefficient
     * @param kC constant
     * @return itself (PointLight)
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * chaining method - set linear attenuation coefficient
     * @param kL linear
     * @return itself (PointLight)
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * chaining method - set quadratic attenuation coefficient
     * @param kQ quadratic
     * @return itself (PointLight)
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
