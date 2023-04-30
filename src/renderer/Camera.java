package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 *  this class represent camera in space
 */
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
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        if (vTo.dotProduct(vUp) != 0)
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

    /** The function returns the camera itself because we want to use it in builder pattern
    * set view plane size based on width and height
    * @param width (double)
    * @param height (double)
    * @return this (camera)
    */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /** The function returns the camera itself because we want to use it in builder pattern
    * set distance between camera and view plane
    * @param distance (double)
    * @return this (camera)
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /** construct ray through pixel
    * @param nX -- rows width
    * @param nY -- columns height
    * @param j -- pixel column
    * @param i -- pixel row
    * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //calculate the center of the view plane
        Vector v_distance = vTo.scale(distance);
        Point pCenter = p0.add(v_distance);

        //set the ratio of the view plane
        if(nX == 0 || nY == 0)
            throw new IllegalArgumentException("resolution cannot be 0");

        double rX = width / nX;
        double rY = height / nY;

        //calculate the step-up/down
        double yi = -(i - (nY - 1) / 2.0) * rY;
        //calculate the step-right/left
        double xj = (j - (nX - 1) / 2.0) * rX;

        //calculate the point on the view plane
        Point p_IJ = pCenter;
        if (!isZero(xj))
        {
            var step  = vRight.scale(xj);
            p_IJ = p_IJ.add(step);
        }
        if (!isZero(yi))
        {
            var step = vUp.scale(yi);
            p_IJ = p_IJ.add(step);
        }
        //calculate the vector from the camera to the point on the view plane
        Vector direction = p_IJ.subtract(p0);
        return new Ray(p0, direction);
    }


}
