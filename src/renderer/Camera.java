package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
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

    private int rays_per_pixel = 1;

    private int threads_count = 1;

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

    public Camera setRayPerPixel(int rayPerPixel) {
        rays_per_pixel = rayPerPixel;
        return this;
    }

    public Camera setThreadsCount(int threadsCount) {
        threads_count = threadsCount;
        return this;
    }

    // ***************** Operations/Methods ********************** //
    /** construct ray through pixel
    * @param nX -- rows width
    * @param nY -- columns height
    * @param j -- pixel column
    * @param i -- pixel row
    * @return ray through the wanted pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point p_IJ = getPixelCenter(nX,nY,j,i);

        //calculate the vector from the camera to the point on the view plane
        Vector direction = p_IJ.subtract(p0);
        return new Ray(p0, direction);
    }


    /** construct rays through pixel for super sampling
     * @param nX -- rows width
     * @param nY -- columns height
     * @param j -- pixel column
     * @param i -- pixel row
     * @return ray through the wanted pixel
     */
    public LinkedList<Ray> constructRays(int nX, int nY, int j, int i) {

        Point p_IJ = getPixelCenter(nX,nY,j,i);
        Point pixelCenter = p_IJ; //save the center point

        //calculate the step of the grid for each pixel
        double xStep = (width / nX)/*rX*/ / rays_per_pixel;
        double yStep = (height / nY)/*rY*/ / rays_per_pixel;

        //create a grid of points for the pixel
        LinkedList<Point> points = pixelCenter.createGrid(rays_per_pixel, xStep, yStep, vRight, vUp);

        //calculate all the rays for each point on greed of the pixel
        LinkedList<Ray> rays = new LinkedList<>();
        for (Point p :points)
        {
            //calculate the vector from the camera to the point on the view plane
            Vector direction = p.subtract(p0);
            rays.add(new Ray(p0, direction));
        }
        return rays;
    }

    /**
     * calculate the center point of a specific pixel
     * @param nX -- rows width
     * @param nY -- columns height
     * @param j -- pixel column
     * @param i -- pixel row
     * @return the center point of the pixel i,j
     */
    private Point getPixelCenter(int nX, int nY, int j, int i) {

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
            p_IJ = p_IJ.add(vRight.scale(xj));

        if (!isZero(yi))
            p_IJ = p_IJ.add(vUp.scale(yi));

        return p_IJ;
    }

    /**
     * check if all the fields are initialized
     * for each pixel in the view plane,
     * construct ray/rays and write the color of the pixel to the image
     * @throws MissingResourceException if one of the fields is null
     */
    public Camera renderImage() {

        checkAllFields();

        final int rows = image.getNx();
        final int columns = image.getNy();

        if (rays_per_pixel ==1)
            renderImage(rows, columns);
        else
            renderImageAntiAliasing(rows, columns);

        return this;
    }


    /**
     * construct rays according to the super sampling and write the color of the pixel to the image
     * using multi threads programming
     * @param rows -- rows width
     * @param columns -- columns height
     */
    private void renderImageAntiAliasing(int rows, int columns) {

        Pixel.initialize(rows,columns, 1);
        while (threads_count-- > 0) {
            new Thread(() -> {
                for(Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()){
                    castRays(rows, columns, pixel.row, pixel.col);
                }
            }).start();
        }
        Pixel.waitToFinish();
    }


    /**
     * construct ray and write the color of the pixel to the image
     * @param rows -- rows width
     * @param columns -- columns height
     */
    private void renderImage(int rows, int columns) {

        Pixel.initialize(rows,columns, 1);
        while (threads_count-- > 0) {
            new Thread(() -> {
                for(Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()){
                    castRay(rows, columns, pixel.row, pixel.col);
                }
            }).start();
        }
        Pixel.waitToFinish();
    }


    /**
     * private method that write the color of the pixel to the image
     * @param nX -- rows width
     * @param nY -- columns height
     * @param j -- pixel column
     * @param i -- pixel row
     */
    private void castRays(int nX, int nY, int i, int j) {

        image.writePixel(i,j,rayTracer.traceRays(constructRays(nX,nY,i,j)));
    }

    /**
     * private method that write the color of the pixel to the image
     * @param nX -- rows width
     * @param nY -- columns height
     * @param j -- pixel column
     * @param i -- pixel row
     */
    private void castRay(int nX, int nY, int i, int j) {

        image.writePixel(i,j,rayTracer.traceRay(constructRay(nX,nY,i,j)));
    }

    /**
     * print grid on the image
     * @param interval the interval between the lines
     * @throws MissingResourceException if the image is null
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
     * check if the image is initialized
     * @throws MissingResourceException if the image is null
     */
    private void imageIsNotNull() {
        if (image == null)
            throw new MissingResourceException("rayTracer not initialized", RayTracerBase.class.getName(), "");
    }


    /**
     * write the image to the file
     * @throws MissingResourceException if the image is null
     */
    public void writeToImage() {

        imageIsNotNull();
        image.writeToImage();
    }

    /**
     * check if all the fields are initialized
     * @throws MissingResourceException if one of the fields is null
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
        if(rays_per_pixel <=0) {
            throw new MissingResourceException("RAYS_PER_PIXEL must be positive", int.class.getName(), "");
        }
    }

}
