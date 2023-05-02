package primitives;


import java.util.List;
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
     * this point intersect with geometry
     *
     * @param t the distance from the starting point
     * @return Point
     */
    public Point getPoint(double t) {

        return p0.add(dir.scale(t));
    }

    /**
     * find and return the closet point to head of the ray
     *
     * @param lst list of point
     * @return the closet point to the head of the ray
     */
    public Point findClosestPoint(List<Point> lst)
    {
        if(lst == null || lst.size() == 0)
            return null;
        //assume the first point is the closet
        int resultIndex = 0;
        double min = p0.distanceSquared(lst.get(resultIndex));//no need to calculate sqrt

        for(int i = 1; i < lst.size(); i++)
        {
            double tmp = p0.distanceSquared(lst.get(i));
            if(tmp < min)
            {
                min = tmp;
                resultIndex = i;
            }
        }
        return lst.get(resultIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
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
