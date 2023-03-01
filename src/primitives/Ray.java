package primitives;

public class Ray {
    final primitives.Point p0;
    final primitives.Vector dir;

    public Ray(primitives.Point p, primitives.Vector v) {

        p0 = p;
        dir = v.normalize();
    }
}
