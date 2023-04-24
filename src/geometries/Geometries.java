package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries{

    private final List<Intersectable> geometries;

    public Geometries() {

        this.geometries = new LinkedList<>();
    }
    public Geometries(Intersectable... geometries) {

        this.geometries = new LinkedList<>();

        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }
    public void add(Intersectable... geometries) {

        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }
    public List<Point> findIntersections(Ray ray) {

        return null;//TODO -- implement this method
    }
}
