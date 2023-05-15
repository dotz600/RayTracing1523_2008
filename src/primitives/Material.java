package primitives;

/**
 * Passive Data Structure
 */
public class Material {

        public Double3 kD = Double3.ZERO; //diffuse (Phong Reflectance Model) coefficient
        public Double3 kS = Double3.ZERO; //specular (Phong Reflectance Model) coefficient
        public int nShininess = 0; //the object’s shininess (Phong Reflectance Model)

    /**
     * chaining method - set diffuse (Phong Reflectance Model) coefficient
     *
     * @param kD Double3 diffuse coefficient
     * @return itself (Material)
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * chaining method - set diffuse (Phong Reflectance Model) coefficient
     *
     * @param kD double diffuse coefficient
     * @return itself (Material)
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * chaining method - set specular (Phong Reflectance Model) coefficient
     *
     * @param kS Double3 specular coefficient
     * @return itself (Material)
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * chaining method - set specular (Phong Reflectance Model) coefficient
     * @param kS double specular coefficient
     * @return itself (Material)
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
