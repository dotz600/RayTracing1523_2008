package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *  this class represent camera in space */
public class Camera {

    // ***************** Fields of Camera class ********************** //
    Point p0;//camera location

    // 3 vectors that represent the camera orientation
    Vector vUp;
    Vector vTo;
    Vector vRight;

    Double width;//view plane width
    Double height;//view plane height
    Double distance;//distance between camera and view plane

    // ***************** Constructors ********************** //
    /**
     * constructor get one point and 2 vertical vectors
     * @param p0 point
     * @param vUp vector
     * @param vTo vector
     */
    public Camera(Point p0, Vector vUp, Vector vTo) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("vUp and vTo must be vertical");
        this.vRight = vTo.crossProduct(vUp).normalize();
        //this.width = 0.0;
        //this.height = 0.0;
        //this.distance = 0.0;
    }


    // ***************** Getters/Setters ********************** //
    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getDistance() {
        return distance;
    }

    // The function returns the camera itself because we want to use it in builder pattern
    /** set view plane size based on width and height
    * @param width (double)
    * @param height (double)
    * @return this (camera)*/
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    // The function returns the camera itself because we want to use it in builder pattern
    /** set distance between camera and view plane
    * @param distance (double)
    * @return this (camera) */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /** construct ray through pixel
    * @param nX -- rows width
    * @param nY -- columns height
    * @param j -- pixel column
    * @param i -- pixel row
    *@return ray */
    public Ray constructRay(int nX, int nY, int j, int i) {

        return null;
    }


}
