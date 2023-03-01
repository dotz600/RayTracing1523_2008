package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {

        super(x,y,z);
        if (Double3.ZERO.equals(super.xyz))
            throw new IllegalArgumentException("Zero vector exception");

    }
    Vector(Double3 xyz) {

        super(xyz);
        if (Double3.ZERO.equals(xyz))
            throw new IllegalArgumentException("Zero vector exception");
    }
    public primitives.Vector add(primitives.Vector v) {

        return new primitives.Vector(this.xyz.add(v.xyz));
    }

    public primitives.Vector scale(double num) {

        return new primitives.Vector(xyz.scale(num));
    }

    public double dotProduct(primitives.Vector v) {

        return xyz.d1*v.xyz.d1 + xyz.d2*v.xyz.d2 + xyz.d3*v.xyz.d3;
    }

    public primitives.Vector crossProduct(primitives.Vector v) {

        return new primitives.Vector(
                this.xyz.d2*v.xyz.d3 - this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1 - this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2 - this.xyz.d2*v.xyz.d1
        );
    }

    public double lengthSquared() {

        return dotProduct(this);
    }

    public double length() {

        return Math.sqrt(lengthSquared());
    }

    public primitives.Vector normalize() {

        return new primitives.Vector(xyz.d1/length(),xyz.d2/length(),xyz.d3/length());
    }

}
