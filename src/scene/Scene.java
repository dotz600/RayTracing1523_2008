package scene;
import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represent the scene
 * contain colors and all the objects in the scene
 * implement builder design pattern with SceneBuilder static inner class
 */
public class Scene {

    // ***************** properties ********************** //
    private final String name;
    private final Color background;
    private final Geometries geometries;
    private final AmbientLight ambientLight;
    private final List<LightSource> lights;

    // ***************** Constructors ********************** //
    /**
     * private constructor with SceneBuilder - build as SceneBuilder
     * only throw SceneBuilder class can be activated
     *
     * @param builder SceneBuilder to build according to it
     */
    private Scene(SceneBuilder builder) {

        this.name = builder.name;
        this.geometries = builder.geometries;
        this.ambientLight = builder.ambientLight;
        this.background = builder.background;
        this.lights = builder.lights;
    }

    /**
     * inner static class - builder design pattern
     * build scene with sceneBuilder and then return built scene
     */
    public static class SceneBuilder{

        private final String name;

        private Color background = Color.BLACK;

        private AmbientLight ambientLight = AmbientLight.NONE;

        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();

        // ***************** setters ********************** //

        /**
         * constructor with name,
         * all other parameters get there default value or by setters
         * @param name of the scene
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * chaining method
         * set background color
         * @param background (Color)
         * @return this (Scene)
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * chaining method
         * set ambientLight color
         * @param ambientLight (AmbientLight)
         * @return this (Scene)
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * chaining method
         * set geometries in scene
         * @param geometries (Geometries)
         * @return this (Scene)
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            if (geometries != null)
                this.geometries = geometries;
            return this;
        }

        /**
         * chaining method
         * set light source in scene
         * @param lights (List of LightSource)
         * @return this (Scene)
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            if (lights != null)
                this.lights = lights;
            return this;
        }

        /**
         * validate all sceneBuilder parameter and return new proper scene
         * @return new Scene
         */
        public Scene build() {
            //Validate Object - nothing to check
            return new Scene(this);
        }

    }

    // ***************** Getters ********************** //

    /**
     * getter - name of the scene
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * getter - background color of the scene
     * @return background (Color)
     */
    public Color getBackground() {
        return background;
    }

    /**
     * getter - geometries in the scene
     * @return geometries (Geometries)
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * getter - ambientLight in the scene
     * @return ambientLight (AmbientLight)
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }


    /**
     * getter - lights in the scene
     * @return lights (List of LightSource)
     */
    public List<LightSource> getLights() { return lights; }
}
