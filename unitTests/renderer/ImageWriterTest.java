package renderer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import primitives.Color;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    @Test
    void writeToImageTests() {

      int gridDensityW = 16, gridDensityH = 10;
      ImageWriter image = new ImageWriter("myFirstImage", 800, 500);
      int gridW = image.getNx()/gridDensityW ;
      int gridH = image.getNy()/gridDensityH;

      for (int i = 0; i < image.getNx(); i++)
      {
          for (int j = 0; j < image.getNy(); j++)
          {
              if(i % gridW == 0|| j % gridH == 0)//if its grid pixel color red
                  image.writePixel(i,j, new Color(255, 0, 0));
              else
                image.writePixel(i,j,new Color(230, 255, 0));//yellow color
          }
      }
      image.writeToImage();
    }

}