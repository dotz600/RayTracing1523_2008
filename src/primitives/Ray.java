package primitives;

import java.util.Objects;

/**
 * This class represent ray in space
 * Contains a starting Point & Vector that represent the direction of the ray
 */
public class Ray {
    /** Starting point */
    final primitives.Point p0;
    /** Direction */
    final primitives.Vector dir;

    /**
     * Constructor with  Point  & Vector
     * @param p Point
     * @param v Vector
     */
    public Ray(primitives.Point p, primitives.Vector v) {

        p0 = p;
        dir = v.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * This method return a point on the ray 𝑷 = 𝑷𝟎 + 𝒕∙𝒗
     * @param t the distance from the starting point
     * @return Point
     */
    public Point getPoint(double t) {//TODO -- refactor -- use this method while calculating intersections
        return p0.add(dir.scale(t));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Ray ray))
            return false;
        return this.dir.equals(((Ray) o).dir) && this.p0.equals(((Ray) o).p0);
    }

    @Override
    public int hashCode() {

        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "point p0=" + p0 +
                ",direction =" + dir +
                '}';
    }
}
