package primitives;

import java.util.Objects;
import java.util.Vector;

public class Point {
    final Double3 xyz;


    public Point(double x, double y, double z) {

        xyz = new Double3(x, y ,z);
    }

    Point(Double3 xyz) {

        this.xyz = xyz;
    }

    public primitives.Vector subtract(Point p) {

        return new primitives.Vector(this.xyz.subtract(p.xyz));
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;

        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {

        return "xyz=" + xyz;
    }

    public Point add(primitives.Vector v) {

        return new Point(xyz.add(v.xyz));
    }

    public double distanceSquared(Point p) {

        return (xyz.d1 - p.xyz.d1)*(xyz.d1 - p.xyz.d1) + (xyz.d2 - p.xyz.d2)*(xyz.d2 - p.xyz.d2)+ (xyz.d3 - p.xyz.d3)*(xyz.d3 - p.xyz.d3);
    }
    public double distance(Point p) {

        return Math.sqrt(distanceSquared(p));
    }
}
