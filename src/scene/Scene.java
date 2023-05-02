package scene;



import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * TODO -- Documentation here
 */
public class Scene {

    public String name;

    public Color background = Color.BLACK;

    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries;

    /**
     * TODO -- Documentation here
     */
    public Scene(String name) {

        this.name = name;
        background = Color.BLACK;
        ambientLight = AmbientLight.NONE;
        geometries = new Geometries();
    }


    /**
     * Builder --
     * set background color
     * @param background (Color)
     * @return this (Scene)
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Builder --
     * set ambientLight color
     * @param ambientLight (AmbientLight)
     * @return this (Scene)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Builder --
     * set geometries in scene
     * @param geometries (Geometries)
     * @return this (Scene)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return  this;
    }
}
