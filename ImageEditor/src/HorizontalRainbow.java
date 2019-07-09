/**
 * This class generates a horizontal rainbow pattern. A horizontal rainbow pattern is generated
 * based off of a width and a height.
 */
public class HorizontalRainbow {
  /**
   * Generates a square rainbow with Red, Orange, Yellow, Green, Blue, Indigo, Violet colored
   * horizontal stripes.
   *
   * @param width  the width of the image (in pixels) to be generated
   * @param height the height of the image (in pixels) to be generated
   * @return an Image object that contains the data of the generated rainbow
   * @throws IllegalArgumentException if the height is less than 16 (this is the smallest possible
   *                                  height to ensure there is at least one stripe of each color),
   *                                  or if the width is less than one (a picture must be at least
   *                                  one pixel wide)
   */
  public Image generate(int width, int height) throws IllegalArgumentException {
    if (height < 16 || width < 1) {
      throw new IllegalArgumentException("Size must be greater than 15");
    }
    return ImageUtil.generateRainbow(width, height, 1);
  }
}
