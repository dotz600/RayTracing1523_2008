package lighting;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SpotLight extends PointLight{

    Vector direction;

    /**
     * constructor - initializes the position, the light intensity and the direction
     *
     * @param pos position of the light source
     * @param intens intensity of the light source
     * @param dir direction of the light source
     */
    public SpotLight( Color intens, Point pos, Vector dir) {
        super(intens,pos);
        this.direction = dir;
    }

    /**
     * get the intensity of the light source at a point
     *
     * @param p the point to get the intensity at
     * @return the intensity of the light source at the point
     */
    @Override
    public Color getIntensity(Point p) {
        Color pointLight = super.getIntensity(p);
        double factor = direction.dotProduct(getL(p));

        return factor <=0 ? Color.BLACK : pointLight.scale(factor);
    }

}
