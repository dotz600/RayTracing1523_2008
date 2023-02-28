package primitives;

import java.util.Objects;
import java.util.Vector;

public class Point {
    Double3 xyz;

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y ,z);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {
        return "xyz=" + xyz;
    }

    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    public Vector subtract(Point p){
        return new Vector()
    }

    public double distanceSquared(Point p){

        return Math.pow(xyz.d1 - p.xyz.d1, 2) + Math.pow(xyz.d2 - p.xyz.d2, 2)+ Math.pow(xyz.d3 - p.xyz.d3, 2);
    }
    public double distance(Point p){
        return Math.sqrt(distanceSquared(p));
    }
}
