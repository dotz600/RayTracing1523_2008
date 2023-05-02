package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 *  this class represent camera in space
 */
public class Camera {

    // ***************** Fields of Camera class ********************** //
    private Point p0;//camera location

    // 3 vectors that represent the camera orientation
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    private Double width;//view plane width
    private Double height;//view plane height
    private Double distance;//distance between camera and view plane


    private ImageWriter image;
    private RayTracerBase rayTracer;

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


    /**
    * Builder --
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


    /**
    * Builder --
    * set distance between camera and view plane
    * @param distance (double)
    * @return this (camera)
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setImageWriter(ImageWriter image) {
        this.image = image;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    // ***************** Operations/Methods ********************** //
    /** construct ray through pixel
    * @param nX -- rows width
    * @param nY -- columns height
    * @param j -- pixel column
    * @param i -- pixel row
    * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //calculate the center of the view plane
        Point pCenter = p0.add(vTo.scale(distance));

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

    /**
     * TODO -- Documentation here
     */
    public void renderImage() {

        checkAllFields();

        final int rows = image.getNx();
        final int columns = image.getNy();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++){
                castRay(rows,columns,i,j);
            }
        }
    }

    /**
     * TODO -- Documentation here
     */
    private void castRay(int nX, int nY, int i, int j) {

        image.writePixel(i,j,rayTracer.traceRay(constructRay(nX,nY,i,j)));
    }

    /**
     * TODO -- Documentation here
     */
    public void printGrid(int interval, Color color){

        imageIsNotNull();

        for (int i = 0; i < image.getNx(); i++) {
            for (int j = 0; j < image.getNy(); j++) {
                if(i % interval == 0|| j % interval == 0)//if its grid pixel color red
                    image.writePixel(i,j, color);

            }
        }
    }

    /**
     * TODO -- Documentation here
     */
    private void imageIsNotNull() {
        if (image == null)
            throw new MissingResourceException("rayTracer not initialized", RayTracerBase.class.getName(), "");
    }


    /**
     * TODO -- Documentation here
     */
    public void writeToImage() {

        imageIsNotNull();
        image.writeToImage();
    }

    /**
     * TODO -- Documentation here
     */
    private void checkAllFields() {

        if (rayTracer == null) {
            throw new MissingResourceException("rayTracer not initialized", RayTracerBase.class.getName(), "");
        }
        if (image == null) {
            throw new MissingResourceException("imageWriter not initialized", ImageWriter.class.getName(), "");
        }
        if (p0 == null) {
            throw new MissingResourceException("p0 not initialized", Point.class.getName(), "");
        }
        if (isZero(height)) {
            throw new MissingResourceException("height not initialized", double.class.getName(), "");
        }
        if (isZero(width)) {
            throw new MissingResourceException("width not initialized", double.class.getName(), "");
        }
        if (isZero(distance)) {
            throw new MissingResourceException("distance not initialized", int.class.getName(), "");
        }
    }

}
