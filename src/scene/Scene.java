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

    private final String name;

    private final Color background;

    private final Geometries geometries;
    private final AmbientLight ambientLight;

    private final List<LightSource> lights;

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
            this.geometries = geometries;
            return this;
        }

        /**
         * chaining method
         * set light source in scene
         * @param lights (List<LightSource>)
         * @return this (Scene)
         */
        public SceneBuilder setLights(List<LightSource> lights) {
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

    //------------Getters ---- Scene Class -------
    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public List<LightSource> getLights() { return lights; }
}
