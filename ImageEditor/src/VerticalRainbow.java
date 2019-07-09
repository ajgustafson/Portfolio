/**
 * This class generates a vertical rainbow pattern. A vertical rainbow pattern is generated based
 * off of a width and a height.
 */
public class VerticalRainbow {
  /**
   * Generates a square rainbow with Red, Orange, Yellow, Green, Blue, Indigo, Violet colored
   * vertical stripes.
   *
   * @param width  the width of the image to be generated
   * @param height the height of the image to be generated
   * @return an Image object that contains the data of the generated rainbow
   * @throws IllegalArgumentException if the width is less than 16 (this is the smallest possible
   *                                  width to ensure there is at least one stripe of each color),
   *                                  or if the height is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  public Image generate(int width, int height) throws IllegalArgumentException {
    if (width < 16 || height < 1) {
      throw new IllegalArgumentException("Size must be greater than 15");
    }
    return ImageUtil.generateRainbow(width, height, 0);
  }
}
